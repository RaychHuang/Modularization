package com.ray.utopia.data.database;

import android.content.Context;

import com.ray.utopia.core.data.database.Database;
import com.ray.utopia.core.data.database.DatabaseImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    static Database provideDatabase(Context context) {
        return new DatabaseImpl(context);
    }
}
