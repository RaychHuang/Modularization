package com.ray.utopia.data.user;

import com.ray.utopia.data.database.Database;

public class UserDaoImpl implements UserDao {

    private final Database mDatabase;

    public UserDaoImpl(Database database) {
        this.mDatabase = database;
    }

    @Override
    public User getUser(String id) {
        return new User(id, "Post", "Malone");
    }
}
