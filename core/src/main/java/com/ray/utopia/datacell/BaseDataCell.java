package com.ray.utopia.datacell;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseDataCell<State> implements DataCell<State> {
    private final BehaviorSubject<State> mStatePublisher = BehaviorSubject.create();
    private final PublishSubject<Object> mIntentPublisher = PublishSubject.create();
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private Seed<State> mSeed;

    public BaseDataCell(Seed<State> seed) {
        this.mSeed = seed;
    }

    @Override
    public synchronized void start() {
        if (mSeed == null) {
            return;
        }

        Scheduler scheduler = createScheduler();
        int cacheNumber = 16;
        Callable<State> initialState = null;
        Function<Object, ObservableSource<Reducer<State>>> handler = null;
        mSeed.seed(mIntentPublisher);

        ConnectableObservable<Reducer<State>> reducers = mIntentPublisher
                .doOnSubscribe(mDisposables::add)
                .observeOn(scheduler)
                .concatMap(this::handleIntent, cacheNumber)
                .publish();

        reducers
                .observeOn(scheduler)
                .scanWith(initialState, this::handleReducer)
                .distinctUntilChanged()
                .subscribe(mStatePublisher);

        reducers.connect(mDisposables::add);
    }

    @Override
    public synchronized void stop() {
        mDisposables.clear();
    }

    @Override
    public <Intent> void sendIntent(Intent intent) {
        mIntentPublisher.onNext(intent);
    }

    @Override
    public State getCurrentState() {
        return mStatePublisher.getValue();
    }

    @Override
    public Observable<State> getState() {
        return mStatePublisher;
    }

    protected String getName() {
        return this.getClass().getSimpleName();
    }

    protected Scheduler createScheduler() {
        String name = "DataCellThread - " + this.getClass().getSimpleName();
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
