package com.ray.utopia.core.data;

import io.reactivex.Observable;

public abstract class Setting<T> extends Observable<T> {

    public abstract T get();

    public abstract void set(T value);
}
