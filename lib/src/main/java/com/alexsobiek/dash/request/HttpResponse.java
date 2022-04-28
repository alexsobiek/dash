package com.alexsobiek.dash.request;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.Getter;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    protected final io.netty.handler.codec.http.HttpResponse parent;
    protected final ByteBuf buffer;

    public HttpResponse(io.netty.handler.codec.http.HttpResponse parent) {
        this.parent = parent;
        try {
            Field bufferField = parent.getClass().getDeclaredField("content");
            bufferField.setAccessible(true);
            buffer = (ByteBuf) bufferField.get(parent);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeString(String string) {
        headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        writeToBuffer(string);
    }

    public void writeJson(String json) {
        headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
        writeToBuffer(json);
    }

    private void writeToBuffer(String string) {
        buffer.writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }

    public HttpResponseStatus status() {
        return parent.status();
    }

    public io.netty.handler.codec.http.HttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
        return parent.setStatus(httpResponseStatus);
    }

    public HttpVersion protocolVersion() {
        return parent.protocolVersion();
    }

    public io.netty.handler.codec.http.HttpResponse setProtocolVersion(HttpVersion httpVersion) {
        return parent.setProtocolVersion(httpVersion);
    }

    public HttpHeaders headers() {
        return parent.headers();
    }

    public DecoderResult decoderResult() {
        return parent.decoderResult();
    }

    public void setDecoderResult(DecoderResult decoderResult) {
        parent.setDecoderResult(decoderResult);
    }
}
