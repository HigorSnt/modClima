package com.cyan.modclima.services;

import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.repositories.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmService {

    private final FarmRepository repository;

    @Autowired
    public FarmService(FarmRepository repository) {
        this.repository = repository;
    }

    public List<Farm> list(Pageable pageable, String name, String code) {
        return repository.findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(pageable, code, name);
    }

    public Optional<Farm> get(Long id) {
        return repository.findById(id);
    }

    public Farm update(Long id, Farm farmUpdated) throws NotFoundException {
        Optional<Farm> optionalFarm = repository.findById(id);

        if (optionalFarm.isEmpty()) {
            throw new NotFoundException(
                    String.format("Farm with id %d not found.", id)
            );
        }

        Farm update = optionalFarm.get().update(farmUpdated);

        return repository.save(update);
    }
}
