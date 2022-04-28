package com.alexsobiek.dash.request;

import com.alexsobiek.dash.request.annotation.GET;
import com.alexsobiek.dash.request.annotation.POST;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RequestHandler {
    default Map<String, RequestMethod> getMethods() {
        Map<String, RequestMethod> methods = new HashMap<>();
        for (Method method : this.getClass().getDeclaredMethods()) {
            Annotation requestMethod = null;
            if (isGet(method)) requestMethod = method.getAnnotation(GET.class);
            else if (isPost(method)) requestMethod = method.getAnnotation(POST.class);
            if (requestMethod != null) {
                if (!method.canAccess(this)) method.setAccessible(true);
                try {
                    methods.put(
                            String.format(
                                    "%s %s",
                                    requestMethod.annotationType().getSimpleName(),
                                    requestMethod.annotationType().getDeclaredMethod("path").invoke(requestMethod)
                            ),
                            new RequestMethod(this, method)
                    );
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return methods;
    }

    private boolean isGet(Method method) {
        return method.isAnnotationPresent(GET.class) && hasReqRes(method);
    }

    private boolean isPost(Method method) {
        return method.isAnnotationPresent(POST.class) && hasReqRes(method);
    }

    private boolean hasReqRes(Method method) {
        return method.getParameterCount() == 2 &&
                hasInterface(getParameterClass(method, 0), HttpRequest.class) &&
                hasInterface(getParameterClass(method, 1), HttpResponse.class);
    }

    private Class<?> getParameterClass(Method method, int index) {
        return method.getParameters()[index].getType();
    }

    private boolean hasInterface(Class<?> _class, Class<?> _interface) {
        return _class.equals(_interface) || List.of(_class.getInterfaces()).contains(_interface);
    }
}
