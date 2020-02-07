package com.ray.utopia.orbit;

import io.reactivex.Observable;

public interface OrbitContainer<STATE, SIDE_EFFECT> {

    STATE getCurrentState();

    Observable<STATE> getOrbit();

    Observable<SIDE_EFFECT> getSideEffect();

    void sendAction(Object action);

    void disposeOrbit();
}
