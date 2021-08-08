package com.rabkov.musictracks.service.impl;

import com.rabkov.musictracks.entity.User;
import com.rabkov.musictracks.exception.RepositoryException;
import com.rabkov.musictracks.exception.ServiceException;
import com.rabkov.musictracks.repository.Specification;
import com.rabkov.musictracks.repository.impl.UserRepositoryImpl;
import com.rabkov.musictracks.service.Service;
import com.rabkov.musictracks.util.PasswordCodec;

import java.util.List;
import java.util.Optional;

public class UserService implements Service<User> {
    private UserRepositoryImpl repository = UserRepositoryImpl.getInstance();
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Optional<User> authenticate(String login, String password) throws ServiceException {
        Optional<User> user =Optional.empty();
        try {
            List<User> userList = repository.query(new FindByLoginSpecification(login));
            if (!userList.isEmpty()) {
                User tempUser = userList.get(0);
                String passwordCode= PasswordCodec.getInstance().codeString(password,login);
                if (passwordCode.equals(tempUser.getPassword())) {
                    user=Optional.of(tempUser);
                }
            }
        } catch (RepositoryException e) {
            //log
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public void insert(User user) throws ServiceException {
        try {
            repository.insert(user);
        } catch (RepositoryException e) {
            //log
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User user) throws ServiceException {
        try {
            repository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> query(Specification specification) throws ServiceException {
        try {
            return repository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}