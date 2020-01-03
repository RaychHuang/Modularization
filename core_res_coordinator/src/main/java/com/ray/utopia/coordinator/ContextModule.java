package com.ray.utopia.coordinator;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
abstract class ContextModule {

    @Binds
    abstract Context provideAppContext(Application app);
}

