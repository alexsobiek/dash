package com.alexsobiek.dash.net;

import com.alexsobiek.dash.DashThreadFactory;
import com.alexsobiek.dash.net.pipeline.Pipeline;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import com.alexsobiek.dash.request.RequestHandler;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

@Getter
public class HttpServer {
    private final Pipeline pipeline;
    private final InetSocketAddress address;
    private final int threads;

    public HttpServer(InetSocketAddress address, int threads) {
        pipeline = new Pipeline();
        this.address = address;
        this.threads = threads;
    }

    public void listen(Consumer<ListenerFuture> consumer) {
        DashThreadFactory.singleThread("Listener", () -> {
            EventLoopGroup group = new NioEventLoopGroup(threads, new DashThreadFactory("Netty"));
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(group);
                bootstrap.channel(NioServerSocketChannel.class);
                bootstrap.localAddress(address);
                bootstrap.childHandler(pipeline);
                ChannelFuture channelFuture = bootstrap.bind().sync();
                channelFuture.addListener(future -> consumer.accept(new ListenerFuture(future)));
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                group.shutdownGracefully();
            }
        }).start();
    }

    public void addHandler(RequestHandler handler) {
        pipeline.getRequestBus().addHandler(handler);
    }

    public void removeHandler(RequestHandler handler) {
        pipeline.getRequestBus().removeHandler(handler);
    }
}
