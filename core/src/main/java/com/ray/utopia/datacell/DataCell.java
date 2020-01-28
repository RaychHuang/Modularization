package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DataCell<State> {

    void start();

    void stop();

    <Intent> void sendIntent(Intent intent);

    State getCurrentState();

    Observable<State> getState();
}
