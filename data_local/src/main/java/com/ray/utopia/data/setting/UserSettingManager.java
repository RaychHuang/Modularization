package com.ray.utopia.data.setting;

import com.ray.utopia.base.Manager;

public interface UserSettingManager extends Manager {

    <T> Setting<T> getSetting(SettingKey<T> key);
}
