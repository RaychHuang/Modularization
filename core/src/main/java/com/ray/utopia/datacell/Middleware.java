package com.ray.utopia.datacell;

import java.util.List;
import java.util.concurrent.Callable;

public class Middleware<S extends State> {

  private final Callable<S> state;
  private final List<Seed<S>> seeds;

  public Middleware(Callable<S> state, List<Seed<S>> seeds) {
    this.state = state;
    this.seeds = seeds;
  }

  Callable<S> getState() {
    return state;
  }

  List<Seed<S>> getSeeds() {
    return seeds;
  }
}
