package com.alexsobiek.dash.net;

import io.netty.util.concurrent.Future;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListenerFuture {
    private final Future<? super Void> future;

    public boolean isSuccess() {
        return future.isSuccess();
    }

    public boolean isCancelled() {
        return future.isCancelled();
    }

    public boolean isDone() {
        return future.isDone();
    }
}
