package com.ray.utopia.orbit;

public interface Reducer<STATE> {

    STATE reduce(STATE state);
}
