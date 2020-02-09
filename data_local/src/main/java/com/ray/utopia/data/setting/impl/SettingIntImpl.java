package com.ray.utopia.data.setting.impl;

import android.content.SharedPreferences;

import com.ray.utopia.data.setting.SettingKey;

import io.reactivex.Scheduler;

class SettingIntImpl extends SettingImpl<Integer> {

    SettingIntImpl(SharedPreferences sp, SettingKey<Integer> key, Scheduler scheduler) {
        super(sp, key, scheduler);
    }

    @Override
    protected Integer read(String key, Integer defValue) {
        return mSp.getInt(key, defValue);
    }

    @Override
    protected void write(String key, Integer value) {
        mSp.edit().putInt(key, value).apply();
    }
}