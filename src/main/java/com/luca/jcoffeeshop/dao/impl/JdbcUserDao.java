package com.luca.jcoffeeshop.dao.impl;

import com.luca.jcoffeeshop.DO.User;
import com.luca.jcoffeeshop.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("jdbc")
public class JdbcUserDao implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void saveUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", user.getUserId());
        params.put("nickname", user.getNickname());
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());

        String sql = "insert into t_user(user_id, nickname, phone_number, username, password) " +
                "values(:userId, :nickname, :phoneNumber, :username, :password)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = "select user_id, nickname, username, phone_number, sign_up_time from t_user " +
                "where username = :username and password = :password";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        RowMapper<User> rowMapper = (rs, rowNum) ->
            User
                .builder()
                .userId(rs.getString("user_id"))
                .nickname(rs.getString("nickname"))
                .username(rs.getString("username"))
                .phoneNumber(rs.getString("phone_number"))
                .signUpTime(rs.getTimestamp("sign_up_time"))
                .build();
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
    }
}
