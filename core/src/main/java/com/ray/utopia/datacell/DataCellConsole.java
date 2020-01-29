package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCellConsole<State, Message> extends DataCell<State, Message> {

    Observable<Object> listenIntent();

    void deliverReducer(Reducer<State> reducer);

    void deliverSideEffect(Reducer<State> reducer);
}
