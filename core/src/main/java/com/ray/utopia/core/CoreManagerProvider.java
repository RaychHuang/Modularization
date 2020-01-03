package com.ray.utopia.core;

public class CoreManagerProvider {

    private static CoreManager INSTANCE = null;

    static void init(CoreManager instance) {
        INSTANCE = instance;
    }

    public static CoreManager getInstance() {
        return INSTANCE;
    }
}
