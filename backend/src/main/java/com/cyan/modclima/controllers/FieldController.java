package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.dtos.FieldDTO;
import com.cyan.modclima.dtos.ShowFieldDTO;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Field;
import com.cyan.modclima.services.FieldService;
import com.cyan.modclima.translators.FieldTranslator;
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
@Api(tags = "Field Controller", produces = "application/json", consumes = "application/json")
@RequestMapping("/fields")
@RestController
public class FieldController {

    private final FieldService service;

    @Autowired
    public FieldController(FieldService service) {
        this.service = service;
    }

    @ApiOperation(value = "Lists all fields and allows filtering by code.")
    @GetMapping
    public ResponseEntity<List<ShowFieldDTO>> list(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String code
    ) {
        return ResponseEntity.ok(service.list(pageable, code));
    }

    @ApiOperation(value = "Retrieves a field from your id.")
    @GetMapping("/{id}")
    public ResponseEntity<ShowFieldDTO> getField(@PathVariable Long id) {
        Optional<Field> field = service.get(id);

        if (field.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ShowFieldDTO(field.get()));
    }

    @ApiOperation(value = "Updates a field.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid FieldDTO field) {
        try {
            return ResponseEntity.ok(service.update(id, FieldTranslator.toModel(field)));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }

}
