package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class BaseMessenger implements Messenger {
  final Subject<Message> messengerI = PublishSubject.create();
  final Subject<Message> messengerO = PublishSubject.create();

  public BaseMessenger(Messenger appMessenger, Scheduler scheduler) {
    messengerI.observeOn(scheduler)
        .subscribe(new Observer<Message>() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onNext(Message message) {
            if (message.getTags().contains("APP")) {
              appMessenger.post(message);
            } else {
              messengerO.onNext(message);
            }
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
        });
  }

  @Override
  public void post(Message message) {
    messengerI.onNext(message);
  }

  @Override
  public Observable<Message> getMessage() {
    return messengerO;
  }
}
