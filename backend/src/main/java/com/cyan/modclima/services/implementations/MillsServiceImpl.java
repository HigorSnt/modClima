package com.cyan.modclima.services.implementations;

import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.MillsRepository;
import com.cyan.modclima.services.MillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MillsServiceImpl implements MillsService {

    private final MillsRepository repository;

    @Autowired
    public MillsServiceImpl(MillsRepository repository) {
        this.repository = repository;
    }

    public Mill create(Mill mill) {
        return repository.save(mill);
    }

    public List<Mill> list(String name) {
        return repository.findAllByNameContainingIgnoreCase(name);
    }

    public Optional<Mill> get(Long id) {
        return repository.findById(id);
    }

    public Mill update(Long id, Mill millUpdated) throws NotFoundException {
        Optional<Mill> optionalMill = repository.findById(id);

        if (optionalMill.isEmpty()) {
            throw new NotFoundException(
                    String.format("Mill with id %d not found.", id)
            );
        }

        Mill update = optionalMill.get().update(millUpdated);

        return repository.save(update);
    }
}
