package com.ray.utopia.core;

import com.ray.utopia.base.Atom;
import com.ray.utopia.base.annotation.NonNull;

public interface CoreInjection {

    default <T extends Atom> T inject(@NonNull Class<T> clazz) {
        return CoreManagerProvider.getInstance().getSystemService(clazz);
    }
}
