package com.ray.utopia.datacell;

public interface Datacell<L extends Ligand, S extends State, M extends Message>
        extends Receptor<L>, Channel<S, M> {
}
