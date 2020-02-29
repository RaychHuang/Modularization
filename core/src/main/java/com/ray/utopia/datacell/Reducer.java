package com.ray.utopia.datacell;

public interface Reducer<S extends State> {

  S reduce(S state);
}
