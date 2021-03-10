package com.cyan.modclima.services;

import com.cyan.modclima.dtos.ShowHarvestDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.repositories.HarvestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class HarvestService {

    private final HarvestRepository repository;

    @Autowired
    public HarvestService(HarvestRepository repository) {
        this.repository = repository;
    }

    public List<ShowHarvestDTO> list(Pageable pageable, LocalDate start, LocalDate end) {
        List<Harvest> list;

        if (start == null || end == null) {
            list = repository.findAll();
        } else {
            list = repository.findAllByStartGreaterThanEqualAndEndLessThanEqual(pageable, start, end);
        }

        return list
                .stream()
                .map(ShowHarvestDTO::new)
                .collect(Collectors.toList());
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
