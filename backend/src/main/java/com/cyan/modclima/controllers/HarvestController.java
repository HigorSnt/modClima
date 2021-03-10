package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.services.HarvestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Api(tags = "Harvest Controller", produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/harvests")
public class HarvestController {

    private final HarvestService service;

    @Autowired
    public HarvestController(HarvestService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lists all harvests and allows filtering between start and end dates.")
    @GetMapping
    public ResponseEntity<List<Harvest>> list(
            @PageableDefault Pageable pageable,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
    ) {
        return ResponseEntity.ok(service.list(pageable, start, end));
    }

    @ApiOperation(value = "Retrieves a harvest from your id.")
    @GetMapping("/{id}")
    public ResponseEntity<Harvest> getHarvest(@PathVariable Long id) {
        Optional<Harvest> harvest = service.get(id);

        if (harvest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(harvest.get());
    }

    @ApiOperation(value = "Updates a harvest.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Harvest harvest) {
        try {
            return ResponseEntity.ok(service.update(id, harvest));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }
}
