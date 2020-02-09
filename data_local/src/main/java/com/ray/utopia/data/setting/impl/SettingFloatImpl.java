package com.ray.utopia.data.setting.impl;

import android.content.SharedPreferences;

import com.ray.utopia.data.setting.SettingKey;

import io.reactivex.Scheduler;

class SettingFloatImpl extends SettingImpl<Float> {

    SettingFloatImpl(SharedPreferences sp, SettingKey<Float> key, Scheduler scheduler) {
        super(sp, key, scheduler);
    }

    @Override
    protected Float read(String key, Float defValue) {
        return mSp.getFloat(key, defValue);
    }

    @Override
    protected void write(String key, Float value) {
        mSp.edit().putFloat(key, value).apply();
    }
}