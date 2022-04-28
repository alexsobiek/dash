package com.alexsobiek.dash.net.pipeline;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.Getter;
import com.alexsobiek.dash.net.ChannelHandler;
import com.alexsobiek.dash.request.RequestBus;

@Getter
public class Pipeline extends ChannelInitializer<SocketChannel> {
    private final ChannelHandler channelHandler;
    private final RequestBus requestBus;

    public Pipeline() {
        channelHandler = new ChannelHandler();
        requestBus = new RequestBus();
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpServerHandler(requestBus));
        pipeline.addLast(channelHandler);
    }
}
