package com.ray.utopia.datacell;

import io.reactivex.Observable;

public interface Messenger {

    void post(Message message);

    Observable<Message> getMessage();
}
