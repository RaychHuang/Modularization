package com.ray.utopia.datacell;

import java.util.List;

public interface Message {

  Throwable getError();

  List<Object> getTags();
}
