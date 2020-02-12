package com.ray.utopia.data.database;

import android.content.Context;

public class DatabaseImpl implements Database {

    private final Context mContext;

    public DatabaseImpl(Context context) {
        this.mContext = context;
    }
}
