package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.dtos.MillDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.services.MillsService;
import com.cyan.modclima.translators.MillTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@Api(tags = "Mills Controller", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/mills")
public class MillsController {

    private final MillsService service;

    @Autowired
    public MillsController(MillsService millsService) {
        this.service = millsService;
    }

    @ApiOperation(value = "Creates a mill.")
    @PostMapping
    public ResponseEntity<Mill> create(@RequestBody @Valid MillDTO mill) {
        return ResponseEntity.status(201).body(service.create(MillTranslator.toModel(mill)));
    }

    @ApiOperation(value = "Lists all mills and allows filtering by name.")
    @GetMapping
    public ResponseEntity<List<Mill>> list(
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "", required = false) String name
    ) {
        return ResponseEntity.ok(service.list(pageable, name));
    }

    @ApiOperation(value = "Retrieves a mill from your id.")
    @GetMapping("/{id}")
    public ResponseEntity<Mill> getMill(@PathVariable Long id) {
        Optional<Mill> mill = service.get(id);

        if (mill.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mill.get());
    }

    @ApiOperation(value = "Updates a mill.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid MillDTO mill) {
        try {
            return ResponseEntity.ok(service.update(id, MillTranslator.toModel(mill)));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }

}
