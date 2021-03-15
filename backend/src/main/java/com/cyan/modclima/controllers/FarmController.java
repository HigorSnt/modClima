package com.cyan.modclima.controllers;

import com.cyan.modclima.dtos.Error;
import com.cyan.modclima.dtos.*;
import com.cyan.modclima.exceptions.NotFoundException;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.services.FarmService;
import com.cyan.modclima.services.implementations.FarmServiceImpl;
import com.cyan.modclima.translators.FarmTranslator;
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
@Api(tags = "Farm Controller", produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/farms")
public class FarmController {

    private final FarmService service;
    private final SimpMessagingTemplate template;

    @Autowired
    public FarmController(FarmServiceImpl service, SimpMessagingTemplate template) {
        this.service = service;
        this.template = template;
    }

    @ApiOperation(value = "Creates a farm.")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateFarmDTO createFarmDTO) {
        try {
            ShowFarmDTO save = service.create(createFarmDTO);
            this.template.convertAndSend("/topic/save", Notification
                    .builder()
                    .message(
                            String.format("A farm %s has been registered!", save.getName())
                    )
                    .redirectURL("/farms/" + save.getId())
                    .build());
            return ResponseEntity.status(201).body(save);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Error.builder().message(e.getMessage()).build()
            );
        }
    }

    @ApiOperation(value = "Lists all farms and allows filtering by name and code.")
    @GetMapping
    public ResponseEntity<List<ShowFarmDTO>> list(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "", required = false) String code
    ) {
        return ResponseEntity.ok(service.list(name, code));
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
