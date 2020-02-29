package com.ray.utopia.datacell;

import java.util.List;
import java.util.concurrent.Callable;

public class Middleware<S extends State, M extends Message> {
    private final Callable<S> initialState;
    private final List<Seed<S, M>> seeds;

    public Middleware(Callable<S> state, List<Seed<S, M>> seeds) {
        this.initialState = state;
        this.seeds = seeds;
    }

    public Callable<S> getInitialState() {
        return initialState;
    }

    public List<Seed<S, M>> getSeeds() {
        return seeds;
    }
}
