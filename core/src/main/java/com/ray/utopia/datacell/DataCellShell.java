package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCellShell<State, Message> extends DataCell<State, Message> {

    Observable<Object> observeIntent();

    void postReducer(Reducer<State> reducer);

    void postMessage(Message reducer);
}
