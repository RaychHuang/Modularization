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
    private final PublishSubject<Object> mIntentPublisher = PublishSubject.create();
    private final PublishSubject<Reducer<State>> mReducerPublisher = PublishSubject.create();
    private final BehaviorSubject<State> mStatePublisher = BehaviorSubject.create();
    private final PublishSubject<Message> mMessagePublisher = PublishSubject.create();
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final Middleware<State, Message> mMiddleware;

    public BaseDataCell(@NonNull Middleware<State, Message> middleware) {
        this.mMiddleware = middleware;
    }

    public synchronized void start() {
        Scheduler scheduler = createScheduler();
        DataCellShellImpl<State, Message> shell = new DataCellShellImpl<>(
                mIntentPublisher, mReducerPublisher, mStatePublisher, mMessagePublisher);

        for (Seed<State, Message> seed : mMiddleware.getSeeds()) {
            seed.plant(shell);
        }

        mReducerPublisher
                .observeOn(scheduler)
                .scanWith(mMiddleware.getInitialState(), this::handleReducer)
                .distinctUntilChanged()
                .subscribe(mStatePublisher);

        mDisposables.add(shell);
    }

    public synchronized void stop() {
        mDisposables.clear();
    }

    @Override
    public <INTENT> void deliverIntent(INTENT intent) {
        mIntentPublisher.onNext(intent);
    }

    @Override
    public State getState() {
        return mStatePublisher.getValue();
    }

    @Override
    public Observable<State> listenState() {
        return mStatePublisher;
    }

    @Override
    public Observable<Message> listenMessage() {
        return mMessagePublisher;
    }

    protected String getName() {
        return this.getClass().getSimpleName();
    }

    protected Scheduler createScheduler() {
        String name = "DataCellThread - " + getName();
        return Schedulers.from(Executors.newSingleThreadExecutor(
                runnable -> new Thread(runnable, name)));
    }

    protected ObservableSource<Reducer<State>> handleIntent(Object intent) {
        return null;
    }

    protected State handleReducer(State oldState, Reducer<State> reducer) {
        State newState = reducer.reduce(oldState);
//        Log.d(getName(), "oldState: " + oldState + ", newState: " + newState);
        return newState;
    }
}
