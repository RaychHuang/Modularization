package com.ray.utopia.core;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public abstract class BaseViewModelProvider extends ViewModelProvider {

    public BaseViewModelProvider(@NonNull ViewModelStoreOwner owner, @NonNull Factory factory) {
        super(owner, factory);
    }

    public BaseViewModelProvider(@NonNull ViewModelStore store, @NonNull Factory factory) {
        super(store, factory);
    }
}
