package com.ray.utopia.datacell;

public interface Receiver<S extends Message> {

    boolean onMessage(S Message);
}
