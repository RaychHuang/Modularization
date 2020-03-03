package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseDatacell<S extends State> implements Datacell<S> {
  private final PublishSubject<Object> intentPublisher = PublishSubject.create();
  private final PublishSubject<Reducer<S>> reducerPublisher = PublishSubject.create();
  private final BehaviorSubject<S> statePublisher = BehaviorSubject.create();
  private final PublishSubject<Message> messagePublisher = PublishSubject.create();

  private final Middleware<S> middleware;

  public BaseDatacell(@NonNull Middleware<S> middleware) {
    this.middleware = middleware;
  }

  public void create() {
    Scheduler scheduler = middleware.getScheduler(this.getClass());

    DatacellShellImpl<S> shell = new DatacellShellImpl<>(
        intentPublisher,
        reducerPublisher,
        statePublisher,
        messagePublisher);

    for (Seed<S> seed : middleware.getSeeds()) {
      seed.plant(shell);
    }

    reducerPublisher
        .observeOn(scheduler)
        .scanWith(middleware::getState, this::handleReducer)
        .subscribe(statePublisher);
  }

  public void destroy() {
    statePublisher.onComplete();
    messagePublisher.onComplete();
    intentPublisher.onComplete();
    reducerPublisher.onComplete();
  }

  @Override
  public <I> void sendIntent(I intent) {
    intentPublisher.onNext(intent);
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
  public Observable<Message> getRxMessage() {
    return messagePublisher;
  }

  protected S handleReducer(S oldState, Reducer<S> reducer) {
    try {
      return reducer.reduce(oldState);
    } catch (Throwable e) {
      Throwable datacellException = new DatacellException(e);
      messagePublisher.onNext(() -> datacellException);
      return oldState;
    }
  }
}
