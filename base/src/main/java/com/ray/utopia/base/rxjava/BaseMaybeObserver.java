package com.ray.utopia.base.rxjava;

import io.reactivex.MaybeObserver;

public abstract class BaseMaybeObserver<T> implements MaybeObserver<T> {
    private final ObserverMarker mMarker = new ObserverMarker();

    @Override
    public void onError(Throwable e) {
    }
}
