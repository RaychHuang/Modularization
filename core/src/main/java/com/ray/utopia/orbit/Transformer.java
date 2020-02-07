package com.ray.utopia.orbit;

import io.reactivex.Observable;

public interface Transformer<STATE, SIDE_EFFECT> {

    Observable<Object> transform(OrbitContext<STATE, SIDE_EFFECT> context);
}
