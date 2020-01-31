package com.ray.utopia.datacell;

public interface Seed<State, Message> {

    void plant(DataCellShell<State, Message> shell);
}
