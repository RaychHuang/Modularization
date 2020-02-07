package com.ray.utopia.orbit;

import java.util.function.Supplier;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public interface OrbitContext<STATE, SIDE_EFFECT> {

    Supplier<STATE> currentStateProvider();

    Observable<Object> getRawActions();

    PublishSubject<Object> getInputSubject();

    PublishSubject<Reducer<STATE>> getReducerSubject();

    PublishSubject<SIDE_EFFECT> getSideEffectSubject();

    boolean isScheduled();
}
