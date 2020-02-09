package com.ray.utopia.data.setting;

import com.ray.utopia.base.annotation.NonNull;

public interface SettingKey<T> {

    @NonNull
    String getDir();

    @NonNull
    String getKey();

    @NonNull
    T getDefValue();
}
