package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.dtos.FarmDTO;
import com.cyan.modclima.dtos.ShowFarmDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.services.FarmService;
import com.cyan.modclima.translators.FarmTranslator;
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

@CrossOrigin("*")
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
    public ResponseEntity<List<ShowFarmDTO>> list(
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String code
    ) {
        return ResponseEntity.ok(service.list(pageable, name, code));
    }

    @ApiOperation(value = "Retrieves a farm from your id.")
    @GetMapping("/{id}")
    public ResponseEntity<ShowFarmDTO> getFarm(@PathVariable Long id) {
        Optional<Farm> farm = service.get(id);

        if (farm.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ShowFarmDTO(farm.get()));
    }

    @ApiOperation(value = "Updates a farm.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid FarmDTO farm) {
        try {
            return ResponseEntity.ok(service.update(id, FarmTranslator.toModel(farm)));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }
}
