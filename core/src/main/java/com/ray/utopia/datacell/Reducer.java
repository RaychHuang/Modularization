package com.ray.utopia.datacell;

public interface Reducer<State> {

    State reduce(State state);
}
