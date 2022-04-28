package com.alexsobiek.dash;

import com.alexsobiek.dash.net.HttpServer;

import java.net.InetSocketAddress;
import java.util.Optional;

public class Dash {
    public static Optional<Thread.UncaughtExceptionHandler> uncaughtExceptionHandler;

    public Dash() {
        uncaughtExceptionHandler = Optional.empty();
    }

    public HttpServer createServer(InetSocketAddress address) {
        return new HttpServer(address, 4);
    }

    public HttpServer createServer(InetSocketAddress address, int threads) {
        return new HttpServer(address, threads);
    }

    public static void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        uncaughtExceptionHandler = Optional.of(handler);
    }
}
