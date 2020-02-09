package com.ray.utopia.base.rxjava;

import io.reactivex.disposables.Disposable;

public class IgnoringObserver<T> extends BaseObserver<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onComplete() {
    }
}
