package com.ray.utopia.core;

import com.ray.utopia.base.Atom;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.MapKey;

@MapKey
@Retention(RetentionPolicy.RUNTIME)
public @interface UnitKey {

    Class<? extends Atom> value();
}
