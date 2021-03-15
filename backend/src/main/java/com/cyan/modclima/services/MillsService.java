package com.cyan.modclima.services;

import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Mill;

import java.util.List;
import java.util.Optional;

public interface MillsService {

    Mill create(Mill mill);

    List<Mill> list(String name);

    Optional<Mill> get(Long id);

    Mill update(Long id, Mill millUpdated) throws NotFoundException;

}
