package com.ray.utopia.datacell;

public interface Receptor<L extends Ligand> {

    void signal(L ligand);
}
