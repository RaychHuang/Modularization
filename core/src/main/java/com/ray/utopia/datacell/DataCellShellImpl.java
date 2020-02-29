package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class DataCellShellImpl<State, Message> implements DataCellShell<State, Message>, Disposable {
    private final CompositeDisposable disposable;
    private final PublishSubject<Object> intentPublisher;
    private final PublishSubject<Reducer<State>> reducerPublisher;
    private final BehaviorSubject<State> statePublisher;
    private final PublishSubject<Message> messagePublisher;

    DataCellShellImpl(PublishSubject<Object> intentPublisher,
                      PublishSubject<Reducer<State>> reducerPublisher,
                      BehaviorSubject<State> statePublisher,
                      PublishSubject<Message> messagePublisher) {
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
    public void postReducer(Reducer<State> reducer) {
        if (!isDisposed()) {
            reducerPublisher.onNext(reducer);
        }
    }

    @Override
    public State getState() {
        return statePublisher.getValue();
    }

    @Override
    public Observable<State> observeState() {
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
}
