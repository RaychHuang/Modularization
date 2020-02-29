package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCellShell<S extends State, M extends Message> extends DataCell<S, M> {

    Observable<Object> observeIntent();

    void postReducer(Reducer<S> reducer);

    void postMessage(M reducer);
}
