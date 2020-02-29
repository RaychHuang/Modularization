package com.ray.utopia.datacell;

public interface Seed<S extends State> {

  void plant(DatacellShell<S> shell);
}
