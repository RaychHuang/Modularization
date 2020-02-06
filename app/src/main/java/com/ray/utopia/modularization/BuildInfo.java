package com.ray.utopia.modularization;

public class BuildInfo implements com.ray.utopia.base.BuildConfig {
    private final String mApplicationId;
    private final String mBuildType;
    private final boolean mDebug;
    private final String mFlavor;
    private final int mVersionCode;
    private final String mVersionName;

    BuildInfo() {
        this.mApplicationId = BuildConfig.APPLICATION_ID;
        this.mBuildType = BuildConfig.BUILD_TYPE;
        this.mDebug = BuildConfig.DEBUG;
        this.mFlavor = BuildConfig.FLAVOR;
        this.mVersionCode = BuildConfig.VERSION_CODE;
        this.mVersionName = BuildConfig.VERSION_NAME;
    }

    @Override
    public String getApplicationId() {
        return mApplicationId;
    }

    @Override
    public String getBuildType() {
        return mBuildType;
    }

    @Override
    public boolean isDebug() {
        return mDebug;
    }

    @Override
    public String getFlavor() {
        return mFlavor;
    }

    @Override
    public int getVersionCode() {
        return mVersionCode;
    }

    @Override
    public String getVersionName() {
        return mVersionName;
    }
}