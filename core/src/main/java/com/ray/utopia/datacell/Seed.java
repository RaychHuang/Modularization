package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Seed<State> {

    <Intent> void seed(Observable<Intent> intent);


}
