package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCell<State, Message> {

    <INTENT> void postIntent(INTENT intent);

    State getState();

    Observable<State> observeState();

    Observable<Message> observeMessage();
}
