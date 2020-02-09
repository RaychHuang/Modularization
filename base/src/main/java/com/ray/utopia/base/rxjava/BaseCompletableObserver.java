package com.ray.utopia.base.rxjava;

import io.reactivex.CompletableObserver;

public abstract class BaseCompletableObserver implements CompletableObserver {
    private final ObserverMarker mMarker = new ObserverMarker();

    @Override
    public void onError(Throwable e) {
    }
}
