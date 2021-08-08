package com.rabkov.musictracks.service;

import com.rabkov.musictracks.exception.ServiceException;
import com.rabkov.musictracks.repository.Specification;

import java.util.List;

public interface Service<T> {
    void insert(T ob) throws ServiceException;
    void update(T ob) throws ServiceException;
    List<T> query(Specification specification) throws ServiceException;
}
