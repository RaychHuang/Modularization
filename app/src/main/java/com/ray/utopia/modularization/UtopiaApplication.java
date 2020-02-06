package com.ray.utopia.modularization;

import com.ray.utopia.core.CoreManagerInitiator;
import com.ray.utopia.core.BaseApplication;

public class UtopiaApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initiate Res
        CoreManagerInitiator.init(this, new BuildInfo());
        // Initiate Orbit Service
    }
}
