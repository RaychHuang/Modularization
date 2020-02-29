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

public abstract class BaseDataCell<S extends State, M extends Message> implements DataCell<S, M> {
    private final PublishSubject<Object> intentPublisher = PublishSubject.create();
    private final PublishSubject<Reducer<S>> reducerPublisher = PublishSubject.create();
    private final BehaviorSubject<S> statePublisher = BehaviorSubject.create();
    private final PublishSubject<M> messagePublisher = PublishSubject.create();
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final Middleware<S, M> middleware;

    private Scheduler scheduler;

    public BaseDataCell(@NonNull Middleware<S, M> middleware) {
        this.middleware = middleware;
    }

    public void create() {
        scheduler = createScheduler();

        DataCellShellImpl<S, M> shell = new DataCellShellImpl<>(
                intentPublisher, reducerPublisher, statePublisher, messagePublisher);

        for (Seed<S, M> seed : middleware.getSeeds()) {
            seed.plant(shell);
        }

        reducerPublisher
                .observeOn(scheduler)
                .scanWith(middleware.getInitialState(), this::handleReducer)
                .subscribe(statePublisher);

        disposable.add(shell);
    }

    public void destroy() {
        disposable.clear();
    }

    @Override
    public <INTENT> void postIntent(INTENT intent) {
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
    public Observable<M> observeMessage() {
        return messagePublisher;
    }

    protected String getName() {
        return this.getClass().getSimpleName();
    }

    protected Scheduler createScheduler() {
        String name = "DataCellThread - " + getName();
        return Schedulers.from(Executors.newSingleThreadExecutor(r -> new Thread(r, name)));
    }

    protected ObservableSource<Reducer<S>> handleIntent(Object intent) {
        return null;
    }

    protected S handleReducer(S oldState, Reducer<S> reducer) {
        try {
            return reducer.reduce(oldState);
        } catch (Throwable e) {
//            messagePublisher.onNext(e);
            return oldState;
        }
    }
}
