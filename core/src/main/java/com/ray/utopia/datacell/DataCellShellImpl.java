package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class DataCellShellImpl<State, Message> implements DataCellShell<State, Message>, Disposable {
    private final CompositeDisposable mDisposable;
    private final PublishSubject<Object> mIntentPublisher;
    private final PublishSubject<Reducer<State>> mReducerPublisher;
    private final BehaviorSubject<State> mStatePublisher;
    private final PublishSubject<Message> mMessagePublisher;

    DataCellShellImpl(PublishSubject<Object> intentPublisher,
                      PublishSubject<Reducer<State>> reducerPublisher,
                      BehaviorSubject<State> statePublisher,
                      PublishSubject<Message> messagePublisher) {
        mDisposable = new CompositeDisposable();
        mIntentPublisher = intentPublisher;
        mReducerPublisher = reducerPublisher;
        mStatePublisher = statePublisher;
        mMessagePublisher = messagePublisher;
    }

    @Override
    public void dispose() {
        mDisposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return mDisposable.isDisposed();
    }

    @Override
    public <INTENT> void postIntent(INTENT intent) {
        if (!isDisposed()) {
            mIntentPublisher.onNext(intent);
        }
    }

    @Override
    public Observable<Object> observeIntent() {
        return mIntentPublisher.doOnSubscribe(mDisposable::add);
    }

    @Override
    public void postReducer(Reducer<State> reducer) {
        if (!isDisposed()) {
            mReducerPublisher.onNext(reducer);
        }
    }

    @Override
    public State getState() {
        return mStatePublisher.getValue();
    }

    @Override
    public Observable<State> observeState() {
        return mStatePublisher.doOnSubscribe(mDisposable::add);
    }

    @Override
    public void postMessage(Message message) {
        if (!isDisposed()) {
            mMessagePublisher.onNext(message);
        }
    }

    @Override
    public Observable<Message> observeMessage() {
        return mMessagePublisher.doOnSubscribe(mDisposable::add);
    }
}
