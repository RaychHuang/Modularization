package com.ray.utopia.data.setting.impl;

import android.content.SharedPreferences;

import com.ray.utopia.data.setting.SettingKey;

import io.reactivex.Scheduler;

class SettingBooleanImpl extends SettingImpl<Boolean> {

    SettingBooleanImpl(SharedPreferences sp, SettingKey<Boolean> key, Scheduler scheduler) {
        super(sp, key, scheduler);
    }

    @Override
    protected Boolean read(String key, Boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    @Override
    protected void write(String key, Boolean value) {
        mSp.edit().putBoolean(key, value).apply();
    }
}