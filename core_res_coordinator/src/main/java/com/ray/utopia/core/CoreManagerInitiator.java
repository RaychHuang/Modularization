package com.ray.utopia.core;

import android.app.Application;

public class CoreManagerInitiator {

    public static void init(Application app) {
        CoreManager manager = new CoreManagerImpl(app);
        CoreManagerProvider.init(manager);
    }
}
