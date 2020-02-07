package com.ray.utopia.orbit;

import java.util.List;

public interface Middleware<STATE, SIDE_EFFECT> {

    STATE getInitialState();

    List<Transformer<STATE, SIDE_EFFECT>> getOrbits();

    Config getConfig();

    interface Config {
        boolean isSideEffectCachingEnabled();
    }
}
