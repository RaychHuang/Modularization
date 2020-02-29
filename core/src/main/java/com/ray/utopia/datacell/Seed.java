package com.ray.utopia.datacell;

public interface Seed<S extends State, M extends Message> {

    void plant(DataCellShell<S, M> shell);
}
