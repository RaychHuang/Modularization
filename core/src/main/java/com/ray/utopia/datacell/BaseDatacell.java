package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseDatacell<S extends State> implements StateOwner<S> {
    private final PublishSubject<Reducer<S>> reducerPublisher = PublishSubject.create();
    private final BehaviorSubject<S> statePublisher = BehaviorSubject.create();

    private final Middleware<S> middleware;

    public BaseDatacell(@NonNull Middleware<S> middleware) {
        this.middleware = middleware;
    }

    public void create() {
        Scheduler scheduler = middleware.getScheduler(this.getClass());
        Media media = middleware.getMedia();

        DatacellShellImpl<S> shell = new DatacellShellImpl<>(

                reducerPublisher,
                statePublisher);

        for (Seed<S> seed : middleware.getSeeds()) {
            seed.plant(shell);
        }

        reducerPublisher
                .observeOn(scheduler)
                .scanWith(middleware::getInitState, this::handleReducer)
                .subscribe(statePublisher);
    }

    public void destroy() {
        statePublisher.onComplete();
        messagePublisher.onComplete();
        intentPublisher.onComplete();
        reducerPublisher.onComplete();
    }

    @Override
    public void send(L Message) {
        intentPublisher.onNext(Message);
    }

    @Override
    public S getState() {
        return statePublisher.getValue();
    }

    @Override
    public Observable<S> getRxState() {
        return statePublisher;
    }

    @Override
    public Observable<M> getRxMessage() {
        return messagePublisher;
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
