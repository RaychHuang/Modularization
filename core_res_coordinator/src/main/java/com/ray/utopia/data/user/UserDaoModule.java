package com.ray.utopia.data.user;

import com.ray.utopia.data.database.Database;

import dagger.Module;
import dagger.Provides;

@Module
public class UserDaoModule {

    @Provides
    static UserDao provideUserDao(Database database) {
        return new UserDaoImpl(database);
    }
}
