package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCell<S extends State, M extends Message> {

    <INTENT> void postIntent(INTENT intent);

    S getState();

    Observable<S> observeState();

    Observable<M> observeMessage();
}
