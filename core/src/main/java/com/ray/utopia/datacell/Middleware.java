package com.ray.utopia.datacell;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public abstract class Middleware<S extends State> {
  private final List<Seed<S>> seeds = new ArrayList<>();

  abstract S getState();

  List<Seed<S>> getSeeds() {
    return seeds;
  }



  protected Scheduler getScheduler(Class clazz) {
    String name = "DatacellThread - " + clazz.getSimpleName();
    return Schedulers.from(Executors.newSingleThreadExecutor(r -> new Thread(r, name)));
  }
}
