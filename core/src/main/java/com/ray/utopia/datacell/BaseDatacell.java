package com.ray.utopia.datacell;

import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseDatacell<S extends State> implements Datacell<S> {

  private final CompositeDisposable disposable = new CompositeDisposable();
  private final PublishSubject<Object> intentPublisher = PublishSubject.create();
  private final PublishSubject<Reducer<S>> reducerPublisher = PublishSubject.create();
  private final BehaviorSubject<S> statePublisher = BehaviorSubject.create();
  private final PublishSubject<Message> messagePublisher = PublishSubject.create();

  private final Middleware<S> middleware;

  public BaseDatacell(@NonNull Middleware<S> middleware) {
    this.middleware = middleware;
  }

  public void create() {
    Scheduler scheduler = createScheduler();

    DatacellShellImpl<S> shell = new DatacellShellImpl<>(
        disposable,
        intentPublisher,
        reducerPublisher,
        statePublisher,
        messagePublisher);

    for (Seed<S> seed : middleware.getSeeds()) {
      seed.plant(shell);
    }

    reducerPublisher
        .observeOn(scheduler)
        .scanWith(middleware.getState(), this::handleReducer)
        .subscribe(statePublisher);
  }

  public void destroy() {
    disposable.clear();
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
  public Observable<S> observeState() {
    return statePublisher;
  }

  @Override
  public Observable<Message> observeMessage() {
    return messagePublisher;
  }

  protected String getName() {
    return this.getClass().getSimpleName();
  }

  protected Scheduler createScheduler() {
    String name = "DataCellThread - " + getName();
    return Schedulers.from(Executors.newSingleThreadExecutor(r -> new Thread(r, name)));
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
