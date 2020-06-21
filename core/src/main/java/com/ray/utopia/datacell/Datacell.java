package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Datacell<S extends State> {

    S getState();

    Observable<S> getRxState();
}
