package com.cyan.modclima.unit.controllers;

import com.cyan.modclima.dtos.CreateFarmDTO;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.FarmRepository;
import com.cyan.modclima.repositories.HarvestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FarmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FarmRepository farmRepository;

    @MockBean
    private HarvestRepository harvestRepository;

    @Test
    public void itShouldReturn201WhenFarmIsCreated() throws Exception {
        Harvest harvest = Harvest
                .builder()
                .id(10L)
                .code("abcde")
                .mill(Mill.builder().build())
                .start(LocalDate.now().plusDays(10))
                .end(LocalDate.now().plusMonths(3))
                .farms(new ArrayList<>())
                .build();
        CreateFarmDTO farm = new CreateFarmDTO("abc", "farm", Collections.emptyList(), 10L);

        Mockito
                .when(harvestRepository.findById(10L))
                .thenReturn(Optional.of(harvest));

        Mockito.when(farmRepository.save(any())).thenReturn(
                Farm.builder().harvest(harvest).build()
        );

        mockMvc.perform(
                post("/farms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(farm))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void itShouldReturn400WhenFarmHarvestIsNotFound() throws Exception {
        CreateFarmDTO farm = new CreateFarmDTO("abc", "farm", Collections.emptyList(), 10L);

        Mockito
                .when(harvestRepository.findById(10L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                post("/farms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(farm))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldReturn200AndList() throws Exception {
        Mockito
                .when(farmRepository.list("", ""))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/farms")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithFarmsFilteredByName() throws Exception {
        Mockito
                .when(farmRepository.list("farm 1", ""))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/farms")
                        .param("name", "farm 1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithFarmsFilteredByCode() throws Exception {
        Mockito
                .when(farmRepository.list("", "abc"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/farms")
                        .param("code", "abc")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn200AndFarmWithId() throws Exception {
        Mockito
                .when(farmRepository.findById(1L))
                .thenReturn(Optional.of(
                        Farm.builder().harvest(Harvest.builder().build()).build()
                ));

        mockMvc
                .perform(get("/farms/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn404IfNotExistsFarmWithId() throws Exception {
        Mockito
                .when(farmRepository.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc
                .perform(get("/farms/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}
