package com.ray.utopia.data.setting.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.ray.utopia.data.setting.Setting;
import com.ray.utopia.data.setting.SettingKey;
import com.ray.utopia.data.setting.UserSettingManager;

import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Scheduler;

public class UserSettingManagerImpl implements UserSettingManager {
    // AppContext
    private final Context mContext;
    private final Scheduler mScheduler;
    private final Map<String, SharedPreferences> mSpCache;
    private final Map<String, Setting> mSettingCache;

    public UserSettingManagerImpl(Context context, Scheduler scheduler) {
        mContext = context;
        mScheduler = scheduler;
        mSpCache = new WeakHashMap<>();
        mSettingCache = new WeakHashMap<>();
    }

    @Override
    public <T> Setting<T> getSetting(SettingKey<T> key) {
        Setting<T> setting;
        synchronized (mSettingCache) {
            //noinspection unchecked
            setting = mSettingCache.get(key.getKey());
            if (setting == null) {
                setting = createSetting(key);
            }
        }
        return setting;
    }

    @SuppressWarnings("unchecked")
    private <T> Setting<T> createSetting(SettingKey<T> key) {
        Setting setting;
        SharedPreferences sp = getOrCreateSharedPreferences(key);

        T defValue = key.getDefValue();
        if (defValue instanceof Boolean) {
            setting = new SettingBooleanImpl(sp, (SettingKey<Boolean>) key, mScheduler);

        } else if (defValue instanceof Float) {
            setting = new SettingFloatImpl(sp, (SettingKey<Float>) key, mScheduler);

        } else if (defValue instanceof Integer) {
            setting = new SettingIntImpl(sp, (SettingKey<Integer>) key, mScheduler);

        } else if (defValue instanceof Long) {
            setting = new SettingLongImpl(sp, (SettingKey<Long>) key, mScheduler);

        } else {
            setting = new SettingStringImpl(sp, (SettingKey<String>) key, mScheduler);
        }

        return mSettingCache.put(key.getKey(), setting);
    }

    private <T> SharedPreferences getOrCreateSharedPreferences(SettingKey<T> key) {
        String dir = key.getDir();
        SharedPreferences sp;
        synchronized (mSpCache) {
            sp = mSpCache.get(dir);
            if (sp == null) {
                sp = mContext.getSharedPreferences(dir, Context.MODE_PRIVATE);
                mSpCache.put(dir, sp);
            }
        }
        return sp;
    }
}
