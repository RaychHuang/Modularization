package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCell<State, Message> {

    <INTENT> void deliverIntent(INTENT intent);

    State getState();

    Observable<State> listenState();

    Observable<Message> listenMessage();
}
