package com.ray.utopia.base;

/**
 * A shadow object of the application BuildConfig for low-level libraries.
 */
public interface BuildConfig {

    String getApplicationId();

    String getBuildType();

    boolean isDebug();

    String getFlavor();

    int getVersionCode();

    String getVersionName();
}
