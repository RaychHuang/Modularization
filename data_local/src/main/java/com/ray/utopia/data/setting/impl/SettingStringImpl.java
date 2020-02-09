package com.ray.utopia.data.setting.impl;

import android.content.SharedPreferences;

import com.ray.utopia.data.setting.SettingKey;

import io.reactivex.Scheduler;

class SettingStringImpl extends SettingImpl<String> {

    SettingStringImpl(SharedPreferences sp, SettingKey<String> key, Scheduler scheduler) {
        super(sp, key, scheduler);
    }

    @Override
    protected String read(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    @Override
    protected void write(String key, String value) {
        mSp.edit().putString(key, value).apply();
    }
}