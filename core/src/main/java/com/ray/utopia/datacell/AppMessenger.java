package com.ray.utopia.datacell;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class AppMessenger implements Messenger {
  final Subject<Message> messengerI = PublishSubject.create();
  final Subject<Message> messengerO = PublishSubject.create();

  public AppMessenger(Scheduler scheduler) {
    messengerI.observeOn(scheduler).subscribe(messengerO);
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
