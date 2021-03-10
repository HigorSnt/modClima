package com.cyan.modclima.services;

import com.cyan.modclima.dtos.ShowFieldDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Field;
import com.cyan.modclima.repositories.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final FieldRepository repository;

    @Autowired
    public FieldService(FieldRepository repository) {
        this.repository = repository;
    }

    public List<ShowFieldDTO> list(Pageable pageable, String code) {
        List<Field> list = repository.findAllByCodeContainingIgnoreCase(pageable, code);

        return list
                .stream()
                .map(ShowFieldDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<Field> get(Long id) {
        return repository.findById(id);
    }

    public Field update(Long id, Field fieldUpdated) throws NotFoundException {
        Optional<Field> optionalField = repository.findById(id);

        if (optionalField.isEmpty()) {
            throw new NotFoundException(
                    String.format("Harvest with id %d not found.", id)
            );
        }

        Field update = optionalField.get().update(fieldUpdated);

        return repository.save(update);
    }
}
