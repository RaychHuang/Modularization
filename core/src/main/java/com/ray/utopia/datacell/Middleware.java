package com.ray.utopia.datacell;

import java.util.List;
import java.util.concurrent.Callable;

public class Middleware<State, Message> {
    private final Callable<State> initialState;
    private final List<Seed<State, Message>> seeds;

    public Middleware(Callable<State> state, List<Seed<State, Message>> seeds) {
        this.initialState = state;
        this.seeds = seeds;
    }

    public Callable<State> getInitialState() {
        return initialState;
    }

    public List<Seed<State, Message>> getSeeds() {
        return seeds;
    }
}
