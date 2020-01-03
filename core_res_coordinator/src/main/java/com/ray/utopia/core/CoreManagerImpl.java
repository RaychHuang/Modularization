package com.ray.utopia.core;

import android.app.Application;

import com.ray.utopia.base.Atom;
import com.ray.utopia.coordinator.CoreManagerComponent;
import com.ray.utopia.coordinator.DaggerCoreManagerComponent;

import java.util.Map;
import java.util.WeakHashMap;

import javax.inject.Inject;

public class CoreManagerImpl implements CoreManager {
    // Can also create a weak LRU Map
    private final WeakHashMap<Class, Object> mCache = new WeakHashMap<>(10);
    @Inject
    Map<Class<? extends Atom>, Atom> mUnitProviders;

    @Inject
    CoreManagerImpl(Application app) {
//        DaggerCoreManagerComponent.factory().create(app).inject(this);
        CoreManagerComponent component = DaggerCoreManagerComponent.builder()
                .application(app)
                .build();
        component.inject(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Atom> T getSystemService(Class<T> clazz) {
        return (T) mUnitProviders.get(clazz);
    }
}
