package com.cyan.modclima.services.implementations;

import com.cyan.modclima.dtos.CreateFarmDTO;
import com.cyan.modclima.dtos.ShowFarmDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.repositories.FarmRepository;
import com.cyan.modclima.repositories.HarvestRepository;
import com.cyan.modclima.services.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository repository;
    private final HarvestRepository harvestRepository;

    @Autowired
    public FarmServiceImpl(FarmRepository repository, HarvestRepository harvestRepository) {
        this.repository = repository;
        this.harvestRepository = harvestRepository;
    }

    @Transactional
    public ShowFarmDTO create(CreateFarmDTO createFarmDTO) throws NotFoundException {
        Optional<Harvest> harvest = harvestRepository.findById(createFarmDTO.getHarvestId());

        if (harvest.isEmpty()) {
            throw new NotFoundException("Harvest not found.");
        }

        Harvest harvest1 = harvest.get();

        Farm build = Farm
                .builder()
                .code(createFarmDTO.getCode())
                .harvest(harvest1)
                .name(createFarmDTO.getName())
                .build();

        Farm save = repository.save(build);

        List<Farm> farms = harvest1.getFarms();
        farms.add(save);
        harvest1.setFarms(farms);

        harvestRepository.save(harvest1);

        return new ShowFarmDTO(save);
    }

    public List<ShowFarmDTO> list(String name, String code) {
        List<Farm> list = repository.list(name, code);

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
