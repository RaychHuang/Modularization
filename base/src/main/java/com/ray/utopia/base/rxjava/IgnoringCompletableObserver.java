package com.ray.utopia.base.rxjava;

import io.reactivex.disposables.Disposable;

public class IgnoringCompletableObserver extends BaseCompletableObserver {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onComplete() {
    }
}
