package com.cyan.modclima.services.implementations;

import com.cyan.modclima.dtos.CreateHarvestDTO;
import com.cyan.modclima.dtos.ShowHarvestDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.HarvestRepository;
import com.cyan.modclima.repositories.MillsRepository;
import com.cyan.modclima.services.HarvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository repository;
    private final MillsRepository millsRepository;

    @Autowired
    public HarvestServiceImpl(HarvestRepository repository, MillsRepository millsRepository) {
        this.repository = repository;
        this.millsRepository = millsRepository;
    }

    public List<ShowHarvestDTO> list(LocalDate start, LocalDate end) {
        List<Harvest> list;

        if (start != null && end != null) {
            list = repository.findAllByStartBetween(start, end);
        } else if (start != null) {
            list = repository.findAllByStartIsGreaterThanEqual(start);
        } else if (end != null) {
            list = repository.findAllByEndIsLessThanEqual(end);
        } else {
            list = repository.findAll();
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

    @Transactional
    public ShowHarvestDTO create(CreateHarvestDTO createHarvestDTO) throws NotFoundException {
        Optional<Mill> mill = millsRepository.findById(createHarvestDTO.getMillId());

        if (mill.isEmpty()) {
            throw new NotFoundException("Mill not found.");
        }

        Mill mill1 = mill.get();

        Harvest harvest = Harvest
                .builder()
                .code(createHarvestDTO.getCode())
                .start(createHarvestDTO.getStart())
                .mill(mill1)
                .end(createHarvestDTO.getEnd())
                .build();

        Harvest save = repository.save(harvest);

        List<Harvest> harvests = mill1.getHarvests();



        harvests.add(save);
        mill1.setHarvests(harvests);

        millsRepository.save(mill1);

        return new ShowHarvestDTO(save);
    }
}
