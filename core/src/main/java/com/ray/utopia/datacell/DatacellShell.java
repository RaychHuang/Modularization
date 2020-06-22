package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DatacellShell<S extends State> {

    S getCurState();

    Observable<S> getState();

    void post(Message message);

    Observable<Message> getMessage();

    void postReducer(Reducer<S> reducer);
}
