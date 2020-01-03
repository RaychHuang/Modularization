package com.ray.utopia.core.data;

import com.ray.utopia.base.Manager;

public interface UserSettingManager extends Manager {

    <T> Setting<T> getSetting(String key);
}
