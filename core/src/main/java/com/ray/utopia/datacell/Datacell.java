package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Datacell<S extends State> {

    S getCurState();

    Observable<S> getState();
}
