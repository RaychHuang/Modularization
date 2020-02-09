package com.ray.utopia.base.rxjava;

import io.reactivex.disposables.Disposable;

public class IgnoringMaybeObserver<T> extends BaseMaybeObserver<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onSuccess(T t) {
    }

    @Override
    public void onComplete() {
    }
}
