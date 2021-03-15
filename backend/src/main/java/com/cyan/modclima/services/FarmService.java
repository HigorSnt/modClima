package com.cyan.modclima.services;

import com.cyan.modclima.dtos.CreateFarmDTO;
import com.cyan.modclima.dtos.ShowFarmDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;

import java.util.List;
import java.util.Optional;

public interface FarmService {

    ShowFarmDTO create(CreateFarmDTO createFarmDTO) throws NotFoundException;

    List<ShowFarmDTO> list(String name, String code);

    Optional<Farm> get(Long id);

    Farm update(Long id, Farm farmUpdated) throws NotFoundException;
}
