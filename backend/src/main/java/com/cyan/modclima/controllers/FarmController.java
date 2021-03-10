package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.services.FarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Farm Controller", produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/farms")
public class FarmController {

    private final FarmService service;

    @Autowired
    public FarmController(FarmService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lists all farms and allows filtering by name and code.")
    @GetMapping
    public ResponseEntity<List<Farm>> list(
            @PageableDefault Pageable pageable,
            @RequestParam String name,
            @RequestParam String code
    ) {
        return ResponseEntity.ok(service.list(pageable, name, code));
    }

    @ApiOperation(value = "Retrieves a farm from your id.")
    @GetMapping("/{id}")
    public ResponseEntity<Farm> getFarm(@PathVariable Long id) {
        Optional<Farm> farm = service.get(id);

        if (farm.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(farm.get());
    }

    @ApiOperation(value = "Updates a farm.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Farm farm) {
        try {
            return ResponseEntity.ok(service.update(id, farm));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }
}
