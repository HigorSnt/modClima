package com.cyan.modclima.services;

import com.cyan.modclima.dtos.CreateFieldDTO;
import com.cyan.modclima.dtos.ShowFieldDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Field;

import java.util.List;
import java.util.Optional;

public interface FieldService {

    ShowFieldDTO create(CreateFieldDTO createFieldDTO) throws NotFoundException;

    List<ShowFieldDTO> list(String code);

    Optional<Field> get(Long id);

    Field update(Long id, Field fieldUpdated) throws NotFoundException;

}
