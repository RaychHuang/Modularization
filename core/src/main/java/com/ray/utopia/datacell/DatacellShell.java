package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DatacellShell<S extends State> {

    S getState();

    Observable<S> getRxState();

    Media getMedia();

    void postReducer(Reducer<S> reducer);
}
