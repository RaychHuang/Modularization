package com.ray.utopia.orbit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseOrbitContainer<STATE, SIDE_EFFECT>
        implements OrbitContainer<STATE, SIDE_EFFECT> {

    private final Middleware<STATE, SIDE_EFFECT> mMiddleware;
    private final STATE mInitialStateOverride;

    private final PublishSubject<Object> mInputSubject = PublishSubject.create();
    private final PublishSubject<Reducer<STATE>> mReducerSubject = PublishSubject.create();
    private final PublishSubject<SIDE_EFFECT> mSideEffectSubject = PublishSubject.create();
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private STATE mCurrentState;
    private ConnectableObservable<STATE> mOrbit;
    private Observable<SIDE_EFFECT> mSideEffect;

    public BaseOrbitContainer(Middleware<STATE, SIDE_EFFECT> middleware) {
        this(middleware, null);
    }

    public BaseOrbitContainer(Middleware<STATE, SIDE_EFFECT> middleware, STATE state) {
        this.mMiddleware = middleware;
        this.mInitialStateOverride = state;
    }

    public STATE getState() {
        return mCurrentState;
    }

    public Observable<STATE> getOrbit() {
        return mOrbit;
    }
}
