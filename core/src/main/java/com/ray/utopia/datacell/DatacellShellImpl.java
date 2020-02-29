package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

class DatacellShellImpl<S extends State> implements DatacellShell<S> {

  private final CompositeDisposable disposable;
  private final PublishSubject<Object> intentPublisher;
  private final PublishSubject<Reducer<S>> reducerPublisher;
  private final BehaviorSubject<S> statePublisher;
  private final PublishSubject<Message> messagePublisher;

  DatacellShellImpl(
      CompositeDisposable disposable,
      PublishSubject<Object> intentPublisher,
      PublishSubject<Reducer<S>> reducerPublisher,
      BehaviorSubject<S> statePublisher,
      PublishSubject<Message> messagePublisher) {
    this.disposable = disposable;
    this.intentPublisher = intentPublisher;
    this.reducerPublisher = reducerPublisher;
    this.statePublisher = statePublisher;
    this.messagePublisher = messagePublisher;
  }

  @Override
  public <I> void sendIntent(I intent) {
    if (!isDisposed()) {
      intentPublisher.onNext(intent);
    }
  }

  @Override
  public Observable<Object> observeIntent() {
    return intentPublisher.doOnSubscribe(disposable::add);
  }

  @Override
  public void postReducer(Reducer<S> reducer) {
    if (!isDisposed()) {
      reducerPublisher.onNext(reducer);
    }
  }

  @Override
  public S getState() {
    return statePublisher.getValue();
  }

  @Override
  public Observable<S> observeState() {
    return statePublisher.doOnSubscribe(disposable::add);
  }

  @Override
  public void postMessage(Message message) {
    if (!isDisposed()) {
      messagePublisher.onNext(message);
    }
  }

  @Override
  public Observable<Message> observeMessage() {
    return messagePublisher.doOnSubscribe(disposable::add);
  }

  private boolean isDisposed() {
    return disposable.isDisposed();
  }
}
