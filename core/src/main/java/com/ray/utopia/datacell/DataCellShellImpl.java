package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class DataCellShellImpl<S extends State, M extends Message> implements DataCellShell<S, M>, Disposable {
    private final CompositeDisposable disposable;
    private final PublishSubject<Object> intentPublisher;
    private final PublishSubject<Reducer<S>> reducerPublisher;
    private final BehaviorSubject<S> statePublisher;
    private final PublishSubject<M> messagePublisher;

    DataCellShellImpl(PublishSubject<Object> intentPublisher,
                      PublishSubject<Reducer<S>> reducerPublisher,
                      BehaviorSubject<S> statePublisher,
                      PublishSubject<M> messagePublisher) {
        this.disposable = new CompositeDisposable();
        this.intentPublisher = intentPublisher;
        this.reducerPublisher = reducerPublisher;
        this.statePublisher = statePublisher;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void dispose() {
        disposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return disposable.isDisposed();
    }

    @Override
    public <INTENT> void postIntent(INTENT intent) {
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
    public void postMessage(M message) {
        if (!isDisposed()) {
            messagePublisher.onNext(message);
        }
    }

    @Override
    public Observable<M> observeMessage() {
        return messagePublisher.doOnSubscribe(disposable::add);
    }
}
