package com.ray.utopia.datacell;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseDataCell<State> implements DataCell<State> {
    private final BehaviorSubject<State> mStatePublisher = BehaviorSubject.create();
    private final Subject<Object> mIntentPublisher = PublishSubject.create();
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

        Scheduler scheduler = createSingleScheduler();
        Callable<State> initialState = null;
        mSeed.seed(mIntentPublisher);

        ConnectableObservable<Reducer<State>> reducers = mIntentPublisher
                .doOnSubscribe(mDisposables::add)
                .observeOn(scheduler)
                .concatMap()
                .observeOn(scheduler)
                .publish();

        reducers
                .doOnSubscribe(mDisposables::add)
                .scanWith(initialState, (oldState, reducer) -> reducer.reduce(oldState))
                .distinctUntilChanged()
                .subscribe(mStatePublisher);

        reducers.connect();
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

    protected Scheduler createSingleScheduler() {
        String name = "DataCell - " + this.getClass().getSimpleName();
        return Schedulers.from(Executors.newSingleThreadExecutor(
                runnable -> new Thread(runnable, name)));
    }
}
