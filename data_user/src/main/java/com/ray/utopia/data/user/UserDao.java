package com.ray.utopia.data.user;

import com.ray.utopia.base.Atom;

public interface UserDao extends Atom {

    User getUser(String id);
}
