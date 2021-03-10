package com.cyan.modclima.services;

import com.cyan.modclima.dtos.ShowFarmDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.repositories.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmService {

    private final FarmRepository repository;

    @Autowired
    public FarmService(FarmRepository repository) {
        this.repository = repository;
    }

    public List<ShowFarmDTO> list(Pageable pageable, String name, String code) {
        List<Farm> list = repository.findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(pageable, code, name);

        return list
                .stream()
                .map(ShowFarmDTO::new)
                .collect(Collectors.toList());
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
