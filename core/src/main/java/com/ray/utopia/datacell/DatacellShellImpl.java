package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

class DatacellShellImpl<S extends State> implements DatacellShell<S> {

  private final PublishSubject<Object> intentPublisher;
  private final PublishSubject<Reducer<S>> reducerPublisher;
  private final BehaviorSubject<S> statePublisher;
  private final PublishSubject<Message> messagePublisher;

  DatacellShellImpl(
      PublishSubject<Object> intentPublisher,
      PublishSubject<Reducer<S>> reducerPublisher,
      BehaviorSubject<S> statePublisher,
      PublishSubject<Message> messagePublisher) {
    this.intentPublisher = intentPublisher;
    this.reducerPublisher = reducerPublisher;
    this.statePublisher = statePublisher;
    this.messagePublisher = messagePublisher;
  }

  @Override
  public <I> void sendIntent(I intent) {
    intentPublisher.onNext(intent);
  }

  @Override
  public Observable<Object> observeIntent() {
    return intentPublisher;
  }

  @Override
  public void postReducer(Reducer<S> reducer) {
    reducerPublisher.onNext(reducer);
  }

  @Override
  public S getState() {
    return statePublisher.getValue();
  }

  @Override
  public Observable<S> getRxState() {
    return statePublisher;
  }

  @Override
  public void postMessage(Message message) {
    messagePublisher.onNext(message);
  }

  @Override
  public Observable<Message> getRxMessage() {
    return messagePublisher;
  }
}
