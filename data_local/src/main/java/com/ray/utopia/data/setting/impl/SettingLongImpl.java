package com.ray.utopia.data.setting.impl;

import android.content.SharedPreferences;

import com.ray.utopia.data.setting.SettingKey;

import io.reactivex.Scheduler;

class SettingLongImpl extends SettingImpl<Long> {

    SettingLongImpl(SharedPreferences sp, SettingKey<Long> key, Scheduler scheduler) {
        super(sp, key, scheduler);
    }

    @Override
    protected Long read(String key, Long defValue) {
        return mSp.getLong(key, defValue);
    }

    @Override
    protected void write(String key, Long value) {
        mSp.edit().putLong(key, value).apply();
    }
}