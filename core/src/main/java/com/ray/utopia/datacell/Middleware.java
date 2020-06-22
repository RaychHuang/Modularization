package com.ray.utopia.datacell;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public abstract class Middleware<S extends State> {
  private final List<Usecase<S>> usecases = new ArrayList<>();

  abstract S getInitState();

  abstract Messenger getMessenger();

  List<Usecase<S>> getUsecases() {
    return usecases;
  }

  protected Scheduler getScheduler(Class clazz) {
    String name = "DatacellThread - " + clazz.getSimpleName();
    return Schedulers.from(Executors.newSingleThreadExecutor(r -> new Thread(r, name)));
  }
}
