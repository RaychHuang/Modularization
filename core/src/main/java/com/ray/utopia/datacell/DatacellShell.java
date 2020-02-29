package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface DatacellShell<S extends State> extends Datacell<S> {

  Observable<Object> observeIntent();

  void postReducer(Reducer<S> reducer);

  void postMessage(Message message);
}
