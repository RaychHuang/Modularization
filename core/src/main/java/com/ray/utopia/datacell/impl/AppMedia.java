package com.ray.utopia.datacell.impl;

import com.ray.utopia.datacell.Receiver;
import com.ray.utopia.datacell.Media;
import com.ray.utopia.datacell.Message;

import java.util.Hashtable;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class AppMedia extends BaseMedia {
    private final Scheduler scheduler;
    private final Map<Receiver, Disposable> receiverMap = new Hashtable<>();
    private final PublishSubject<Message> messagePublisher = PublishSubject.create();

    public AppMedia(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Media getRoot() {
        return this;
    }

    @Override
    public void publish(Message message) {
        messagePublisher.onNext(message);
    }

    @Override
    public void subscribe(Receiver receiver) {
        InterConsumer consumer = new InterConsumer(receiverMap, receiver);
        messagePublisher
                .observeOn(scheduler)
                .subscribe(consumer);
    }

    @Override
    public void unsubscribe(Receiver receiver) {
        Disposable d = receiverMap.remove(receiver);
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }

    private static final class InterConsumer implements Observer<Message> {
        private final Map<Receiver, Disposable> map;
        private final Receiver receiver;

        private InterConsumer(Map<Receiver, Disposable> map, Receiver receiver) {
            this.map = map;
            this.receiver = receiver;
        }

        @Override
        public void onSubscribe(Disposable d) {
            map.put(receiver, d);
        }

        @Override
        public void onNext(Message message) {
            receiver.onMessage(message);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    }
}
