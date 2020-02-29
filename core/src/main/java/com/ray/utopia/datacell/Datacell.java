package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Datacell<S extends State> {

  <I> void sendIntent(I intent);

  S getState();

  Observable<S> observeState();

  Observable<Message> observeMessage();
}
