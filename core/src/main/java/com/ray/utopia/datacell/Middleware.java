package com.ray.utopia.datacell;

import java.util.List;
import java.util.concurrent.Callable;

public class Middleware<State, Message> {
    private final Callable<State> mInitialState;
    private final List<Seed<State, Message>> mSeeds;

    public Middleware(Callable<State> state, List<Seed<State, Message>> seeds) {
        this.mInitialState = state;
        this.mSeeds = seeds;
    }

    public Callable<State> getInitialState() {
        return mInitialState;
    }

    public List<Seed<State, Message>> getSeeds() {
        return mSeeds;
    }
}
