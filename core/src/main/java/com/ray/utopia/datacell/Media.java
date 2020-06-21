package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Media {

    void publish(Message message);

    Observable<Message> getMessage();
}
