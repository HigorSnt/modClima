package com.cyan.modclima.services;

import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.repositories.HarvestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HarvestService {

    private final HarvestRepository repository;

    @Autowired
    public HarvestService(HarvestRepository repository) {
        this.repository = repository;
    }

    public List<Harvest> list(Pageable pageable, LocalDate start, LocalDate end) {
        if (start != null && end != null) {
            return repository.findAll();
        }

        return repository.findAllByStartGreaterThanEqualAndEndLessThanEqual(pageable, start, end);
    }

    public Optional<Harvest> get(Long id) {
        return repository.findById(id);
    }

    public Harvest update(Long id, Harvest harvestUpdated) throws NotFoundException {
        Optional<Harvest> optionalHarvest = repository.findById(id);

        if (optionalHarvest.isEmpty()) {
            throw new NotFoundException(
                    String.format("Harvest with id %d not found.", id)
            );
        }

        Harvest update = optionalHarvest.get().update(harvestUpdated);

        return repository.save(update);
    }
}
