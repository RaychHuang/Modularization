package com.ray.utopia.datacell;

import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseDataCell<State, Message> implements DataCell<State, Message> {
    private final PublishSubject<Object> intentPublisher = PublishSubject.create();
    private final PublishSubject<Reducer<State>> reducerPublisher = PublishSubject.create();
    private final BehaviorSubject<State> statePublisher = BehaviorSubject.create();
    private final PublishSubject<Message> messagePublisher = PublishSubject.create();
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final Middleware<State, Message> middleware;

    public BaseDataCell(@NonNull Middleware<State, Message> middleware) {
        this.middleware = middleware;
    }

    public synchronized void start() {
        Scheduler scheduler = createScheduler();
        DataCellShellImpl<State, Message> shell = new DataCellShellImpl<>(
                intentPublisher, reducerPublisher, statePublisher, messagePublisher);

        for (Seed<State, Message> seed : middleware.getSeeds()) {
            seed.plant(shell);
        }

        reducerPublisher
                .observeOn(scheduler)
                .scanWith(middleware.getInitialState(), this::handleReducer)
                .distinctUntilChanged()
                .subscribe(statePublisher);

        disposable.add(shell);
    }

    public synchronized void stop() {
        disposable.clear();
    }

    @Override
    public <INTENT> void postIntent(INTENT intent) {
        intentPublisher.onNext(intent);
    }

    @Override
    public State getState() {
        return statePublisher.getValue();
    }

    @Override
    public Observable<State> observeState() {
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

    protected ObservableSource<Reducer<State>> handleIntent(Object intent) {
        return null;
    }

    protected State handleReducer(State oldState, Reducer<State> reducer) {
        try {
            return reducer.reduce(oldState);
        } catch (Exception e) {
            return oldState;
        }
    }
}
