package com.ray.utopia.core;

import android.app.Application;

import com.ray.utopia.base.BuildConfig;

public class CoreManagerInitiator {

    public static void init(Application app, BuildConfig buildConfig) {
        CoreManager manager = new CoreManagerImpl(app);
        CoreManagerProvider.init(manager);
    }
}
