package com.ray.utopia.core.data;

public class SettingsKey {
    private static final SettingsKey ourInstance = new SettingsKey();

    public static SettingsKey getInstance() {
        return ourInstance;
    }

    private SettingsKey() {
    }
}
