package com.alexsobiek.dash;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;

@RequiredArgsConstructor
public class DashThreadFactory implements ThreadFactory {
    private final String name;
    private int count = 0;

    @Override
    public Thread newThread(Runnable runnable) {
        count++;
        String threadName = String.format("%s #%d", name, count);
        Thread thread = new Thread(runnable, threadName);
        Dash.uncaughtExceptionHandler.ifPresent(thread::setUncaughtExceptionHandler);

        return thread;
    }

    public static Thread singleThread(String name, Runnable runnable) {
        Thread thread = new Thread(runnable, name);
        Dash.uncaughtExceptionHandler.ifPresent(thread::setUncaughtExceptionHandler);
        return thread;
    }
}
