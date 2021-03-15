package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.dtos.*;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.services.HarvestService;
import com.cyan.modclima.services.implementations.HarvestServiceImpl;
import com.cyan.modclima.translators.HarvestTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@Api(tags = "Harvest Controller", produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/harvests")
public class HarvestController {

    private final HarvestService service;
    private final SimpMessagingTemplate template;

    @Autowired
    public HarvestController(HarvestServiceImpl service, SimpMessagingTemplate template) {
        this.service = service;
        this.template = template;
    }

    @ApiOperation(value = "Creates a harvest.")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateHarvestDTO createHarvestDTO) throws NotFoundException {
        try {
            ShowHarvestDTO save = service.create(createHarvestDTO);
            this.template.convertAndSend("/topic/save", Notification
                    .builder()
                    .message(
                            String.format("A harvest %s has been registered!", save.getCode())
                    )
                    .redirectURL("/harvests/" + save.getId())
                    .build());
            return ResponseEntity.status(201).body(save);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }

    @ApiOperation(value = "Lists all harvests and allows filtering between start and end dates.")
    @GetMapping
    public ResponseEntity<List<ShowHarvestDTO>> list(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(service.list(start, end));
    }

    @ApiOperation(value = "Retrieves a harvest from your id.")
    @GetMapping("/{id}")
    public ResponseEntity<ShowHarvestDTO> getHarvest(@PathVariable Long id) {
        Optional<Harvest> harvest = service.get(id);

        if (harvest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ShowHarvestDTO(harvest.get()));
    }

    @ApiOperation(value = "Updates a harvest.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid HarvestDTO harvest) {
        try {
            return ResponseEntity.ok(service.update(id, HarvestTranslator.toModel(harvest)));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }
}
