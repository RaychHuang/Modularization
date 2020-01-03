package com.ray.utopia.core;

import com.ray.utopia.base.Atom;
import com.ray.utopia.base.annotation.NonNull;
import com.ray.utopia.base.annotation.Nullable;

public interface CoreManager {

    @Nullable
    <T extends Atom> T getSystemService(@NonNull Class<T> clazz);
}
