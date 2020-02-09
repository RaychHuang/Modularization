package com.ray.utopia.base.rxjava;

import io.reactivex.disposables.Disposable;

public class IgnoringSingleObserver<T> extends BaseSingleObserver<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onSuccess(T t) {
    }
}
