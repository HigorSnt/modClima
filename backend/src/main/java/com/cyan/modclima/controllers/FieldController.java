package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.dtos.*;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Field;
import com.cyan.modclima.services.FieldService;
import com.cyan.modclima.services.implementations.FieldServiceImpl;
import com.cyan.modclima.translators.FieldTranslator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate template;

    @Autowired
    public FieldController(FieldServiceImpl service, SimpMessagingTemplate template) {
        this.service = service;
        this.template = template;
    }

    @ApiOperation(value = "Creates a field.")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateFieldDTO createFieldDTO) throws NotFoundException {
        try {
            ShowFieldDTO save = service.create(createFieldDTO);
            this.template.convertAndSend("/topic/save", Notification
                    .builder()
                    .message(
                            String.format("A field %s has been registered!", save.getCode())
                    )
                    .redirectURL("/fields/" + save.getId())
                    .build());
            return ResponseEntity.status(201).body(save);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }

    @ApiOperation(value = "Lists all fields and allows filtering by code.")
    @GetMapping
    public ResponseEntity<List<ShowFieldDTO>> list(
            @RequestParam(required = false, defaultValue = "") String code
    ) {
        return ResponseEntity.ok(service.list(code));
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
