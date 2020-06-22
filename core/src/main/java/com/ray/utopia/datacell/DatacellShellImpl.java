package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

class DatacellShellImpl<S extends State> implements DatacellShell<S> {

  private final Datacell<S> datacell;
  private final Messenger messenger;
  private final PublishSubject<Reducer<S>> reducerPublisher;

  DatacellShellImpl(Datacell<S> datacell, Messenger messenger,
      PublishSubject<Reducer<S>> reducerPublisher) {
    this.datacell = datacell;
    this.messenger = messenger;
    this.reducerPublisher = reducerPublisher;
  }

  @Override
  public S getCurState() {
    return datacell.getCurState();
  }

  @Override
  public Observable<S> getState() {
    return datacell.getState();
  }

  @Override
  public void post(Message message) {
    messenger.post(message);
  }

  @Override
  public Observable<Message> getMessage() {
    return messenger.getMessage();
  }

  @Override
  public void postReducer(Reducer<S> reducer) {
    reducerPublisher.onNext(reducer);
  }
}
