package com.luca.jcoffeeshop.dao;

import com.luca.jcoffeeshop.DO.User;

public interface UserDao {

    void saveUser(User user);

    User getUserByUsernameAndPassword(String username, String password);
}
