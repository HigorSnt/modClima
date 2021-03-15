package com.cyan.modclima.services;

import com.cyan.modclima.dtos.CreateHarvestDTO;
import com.cyan.modclima.dtos.ShowHarvestDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Harvest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HarvestService {

    List<ShowHarvestDTO> list(LocalDate start, LocalDate end);

    Optional<Harvest> get(Long id);

    Harvest update(Long id, Harvest harvestUpdated) throws NotFoundException;

    ShowHarvestDTO create(CreateHarvestDTO createHarvestDTO) throws NotFoundException;

}
