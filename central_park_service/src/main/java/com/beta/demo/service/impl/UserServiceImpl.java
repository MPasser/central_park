package com.beta.demo.service.impl;

import com.beta.demo.pojo.User;
import com.beta.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Override
    public List<User> findAll() {
        // TODO
        return null;
    }

    @Override
    public User findById(int id) {
        // TODO
        return null;
    }

    @Override
    public List<User> findByUsername(String username) {
        // TODO
        return null;
    }

    @Override
    public void add(User user) {
        // TODO
    }

    @Override
    public void removeById(int id) {
        // TODO
    }

    @Override
    public void modify(User user) {
        // TODO
    }
}
