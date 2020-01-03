package com.ray.utopia.data;

import com.ray.utopia.base.Atom;
import com.ray.utopia.core.UnitKey;
import com.ray.utopia.data.user.UserDao;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class UnitModule {

    @Binds
    @IntoMap
    @UnitKey(UserDao.class)
    abstract Atom provideUserDaoUnit(UserDao userDao);
}
