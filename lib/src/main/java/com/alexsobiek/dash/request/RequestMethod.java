package com.alexsobiek.dash.request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record RequestMethod(RequestHandler handler, Method method) {
    public void invoke(HttpRequest request, HttpResponse response) throws InvocationTargetException, IllegalAccessException {
        method.invoke(handler, request, response);
    }
}
