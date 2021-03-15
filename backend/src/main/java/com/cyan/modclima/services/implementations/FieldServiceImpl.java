package com.cyan.modclima.services.implementations;

import com.cyan.modclima.dtos.CreateFieldDTO;
import com.cyan.modclima.dtos.ShowFieldDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.models.Field;
import com.cyan.modclima.repositories.FarmRepository;
import com.cyan.modclima.repositories.FieldRepository;
import com.cyan.modclima.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository repository;
    private final FarmRepository farmRepository;

    @Autowired
    public FieldServiceImpl(FieldRepository repository, FarmRepository farmRepository) {
        this.repository = repository;
        this.farmRepository = farmRepository;
    }

    @Transactional
    public ShowFieldDTO create(CreateFieldDTO createFieldDTO) throws NotFoundException {
        Optional<Farm> farm = farmRepository.findById(createFieldDTO.getFarmId());

        if (farm.isEmpty()) {
            throw new NotFoundException("Farm not found.");
        }

        Farm farm1 = farm.get();

        Field build = Field
                .builder()
                .code(createFieldDTO.getCode())
                .farm(farm1)
                .geom(createFieldDTO.getGeom())
                .build();

        Field save = repository.save(build);

        List<Field> fields = farm1.getFields();
        fields.add(save);
        farm1.setFields(fields);

        farmRepository.save(farm1);

        return new ShowFieldDTO(save);
    }

    public List<ShowFieldDTO> list(String code) {
        List<Field> list = repository.findAllByCodeContainingIgnoreCase(code);

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
