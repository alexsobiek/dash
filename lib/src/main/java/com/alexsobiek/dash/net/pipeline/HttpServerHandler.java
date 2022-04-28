package com.alexsobiek.dash.net.pipeline;

import com.alexsobiek.dash.request.HttpResponse;
import com.alexsobiek.dash.request.RequestBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {
    private final RequestBus bus;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof HttpRequest req) {
            String requestName = String.format("%s %s", req.method().name(), req.uri());

            // Test response
            DefaultHttpResponse res = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK
            );

            bus.handleRequest(new com.alexsobiek.dash.request.HttpRequest(req), new HttpResponse(res));
            ctx.writeAndFlush(res);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
