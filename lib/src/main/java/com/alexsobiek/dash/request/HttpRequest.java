package com.alexsobiek.dash.request;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpRequest {
    protected final io.netty.handler.codec.http.HttpRequest parent;

    public HttpMethod method() {
        return parent.method();
    }

    public io.netty.handler.codec.http.HttpRequest setMethod(HttpMethod method) {
        return parent.setMethod(method);
    }

    public String uri() {
        return parent.uri();
    }

    public io.netty.handler.codec.http.HttpRequest setUri(String uri) {
        return parent.setUri(uri);
    }

    public io.netty.handler.codec.http.HttpRequest setProtocolVersion(HttpVersion version) {
        return parent.setProtocolVersion(version);
    }

    public HttpHeaders headers() {
        return parent.headers();
    }

    public DecoderResult decoderResult() {
        return parent.decoderResult();
    }

    public void setDecoderResult(DecoderResult result) {
        parent.setDecoderResult(result);
    }
}
