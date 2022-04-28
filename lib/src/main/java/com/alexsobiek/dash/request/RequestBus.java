package com.alexsobiek.dash.request;

import io.netty.handler.codec.http.HttpHeaderNames;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class RequestBus {
    private final HashMap<String, RequestMethod> requestHandlers;

    public RequestBus() {
        requestHandlers = new HashMap<>();
    }

    public void addHandler(RequestHandler handler) {
        requestHandlers.putAll(handler.getMethods());
    }

    public void removeHandler(RequestHandler handler) {
        handler.getMethods().keySet().forEach(requestHandlers::remove);
    }

    public void handleRequest(HttpRequest req, HttpResponse res) {
        String requestName = String.format("%s %s", req.method().name(), req.uri());
        RequestMethod method = null;
        if (requestHandlers.containsKey(requestName)) method = requestHandlers.get(requestName);
        else {
            String wildcardName = String.format("%s *", req.method().name());
            if (requestHandlers.containsKey(wildcardName)) method = requestHandlers.get(wildcardName);
        }

        if (method != null) {
            try {
                method.invoke(req, res);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Default 404
            res.writeString((String.format("Could not %s", requestName)));
        }
        res.headers().set(HttpHeaderNames.CONTENT_LENGTH, res.buffer.readableBytes());
    }
}
