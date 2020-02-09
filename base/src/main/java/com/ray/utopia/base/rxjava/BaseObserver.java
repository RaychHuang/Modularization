package com.ray.utopia.base.rxjava;

import io.reactivex.Observer;

public abstract class BaseObserver<T> implements Observer<T> {
    private final ObserverMarker mMarker = new ObserverMarker();

    @Override
    public void onError(Throwable e) {
    }
}
