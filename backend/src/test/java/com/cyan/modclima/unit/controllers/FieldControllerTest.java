package com.cyan.modclima.unit.controllers;

import com.cyan.modclima.dtos.CreateFarmDTO;
import com.cyan.modclima.dtos.CreateFieldDTO;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.models.Field;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.repositories.FarmRepository;
import com.cyan.modclima.repositories.FieldRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FieldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FarmRepository farmRepository;

    @MockBean
    private FieldRepository fieldRepository;

    @Test
    public void itShouldReturn201WhenFarmIsCreated() throws Exception {
        Farm farm = Farm
                .builder()
                .id(10L)
                .code("abcde")
                .harvest(Harvest.builder().build())
                .fields(new ArrayList<>())
                .build();

        GeometryFactory factory = new GeometryFactory();
        CreateFieldDTO field = new CreateFieldDTO(
                "abc",
                factory.createPoint(new Coordinate(-7.56516516, 30.5445544)),
                10L
        );

        Mockito
                .when(farmRepository.findById(10L))
                .thenReturn(Optional.of(farm));

        Mockito.when(fieldRepository.save(any())).thenReturn(
                Field
                        .builder()
                        .geom(factory.createPoint(new Coordinate(-7.56516516, 30.5445544)))
                        .farm(farm)
                        .build()
        );

        mockMvc.perform(
                post("/fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(field))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void itShouldReturn400WhenFieldFarmIsNotFound() throws Exception {
        CreateFarmDTO farm = new CreateFarmDTO("abc", "farm", Collections.emptyList(), 10L);

        Mockito
                .when(farmRepository.findById(10L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                post("/fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(farm))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldReturn200AndList() throws Exception {
        Mockito
                .when(fieldRepository.findAllByCodeContainingIgnoreCase(""))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/fields")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithFarmsFilteredByCode() throws Exception {
        Mockito
                .when(fieldRepository.findAllByCodeContainingIgnoreCase("kdkcmd"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/fields")
                        .param("code", "kdkcmd")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn200AndFieldWithId() throws Exception {
        Farm farm = Farm
                .builder()
                .id(10L)
                .code("abcde")
                .harvest(Harvest.builder().build())
                .fields(new ArrayList<>())
                .build();
        GeometryFactory factory = new GeometryFactory();
        Mockito
                .when(fieldRepository.findById(1L))
                .thenReturn(Optional.of(
                        Field
                                .builder()
                                .farm(farm)
                                .geom(factory.createPoint(new Coordinate(-7.56516516, 30.5445544)))
                                .build()
                ));

        mockMvc
                .perform(get("/fields/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn404IfNotExistsFieldWithId() throws Exception {
        Mockito
                .when(fieldRepository.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc
                .perform(get("/fields/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}
