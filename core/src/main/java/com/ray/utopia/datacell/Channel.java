package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Channel<S extends State, M extends Message> {

    S getState();

    Observable<S> getRxState();

    Observable<M> getRxMessage();
}
