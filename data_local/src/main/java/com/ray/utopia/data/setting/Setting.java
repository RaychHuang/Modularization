package com.ray.utopia.data.setting;

import com.ray.utopia.base.annotation.AnyThread;
import com.ray.utopia.base.annotation.NonNull;
import com.ray.utopia.base.annotation.WorkerThread;

import io.reactivex.Observable;

public abstract class Setting<T> extends Observable<T> {

    @NonNull
    @WorkerThread
    public abstract T get();

    @AnyThread
    public abstract void set(@NonNull T value);
}
