package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseDatacell<S extends State> implements Datacell<S> {

  private final PublishSubject<Reducer<S>> reducerPublisher = PublishSubject.create();
  private final BehaviorSubject<S> statePublisher = BehaviorSubject.create();

  private final Middleware<S> middleware;

  public BaseDatacell(@NonNull Middleware<S> middleware) {
    this.middleware = middleware;
  }

  public void create() {
    Scheduler scheduler = middleware.getScheduler(this.getClass());
    Messenger messenger = middleware.getMessenger();

    DatacellShellImpl<S> shell = new DatacellShellImpl<>(
        this,
        messenger,
        reducerPublisher);

    for (Usecase<S> usecase : middleware.getUsecases()) {
      usecase.apply(shell);
    }

    reducerPublisher
        .observeOn(scheduler)
        .scanWith(middleware::getInitState, this::handleReducer)
        .subscribe(statePublisher);
  }

  public void destroy() {
    statePublisher.onComplete();
    reducerPublisher.onComplete();
  }

  @Override
  public S getCurState() {

    return statePublisher.getValue();
  }

  @Override
  public Observable<S> getState() {
    return statePublisher;
  }

  protected S handleReducer(S oldState, Reducer<S> reducer) {
    try {
      return reducer.reduce(oldState);
    } catch (Throwable e) {
      Throwable datacellException = new DatacellException(e);
//            messagePublisher.onNext(() -> datacellException);
      return oldState;
    }
  }
}
