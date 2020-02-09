package com.ray.utopia.base.rxjava;

import io.reactivex.SingleObserver;

public abstract class BaseSingleObserver<T> implements SingleObserver<T> {
    private final ObserverMarker mMarker = new ObserverMarker();

    @Override
    public void onError(Throwable e) {
    }
}
