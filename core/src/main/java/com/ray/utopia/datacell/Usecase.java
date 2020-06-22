package com.ray.utopia.datacell;

public interface Usecase<S extends State> {

  void apply(DatacellShell<S> shell);
}
