package com.cyan.modclima.unit.controllers;

import com.cyan.modclima.dtos.CreateHarvestDTO;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.HarvestRepository;
import com.cyan.modclima.repositories.MillsRepository;
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
public class HarvestsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MillsRepository millsRepository;

    @MockBean
    private HarvestRepository harvestRepository;

    @Test
    public void itShouldReturn201WhenHarvestIsCreated() throws Exception {
        Mill mill = Mill
                .builder()
                .id(10L)
                .name("mill")
                .harvests(new ArrayList<>())
                .build();

        CreateHarvestDTO harvest = new CreateHarvestDTO(
                "abc",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusMonths(3),
                Collections.emptyList(),
                10L
        );

        Mockito
                .when(millsRepository.findById(10L))
                .thenReturn(Optional.of(mill));

        Mockito.when(harvestRepository.save(any())).thenReturn(
                Harvest.builder().mill(mill).build()
        );

        mockMvc.perform(
                post("/harvests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(harvest))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void itShouldReturn400WhenHarvestMillIsNotFound() throws Exception {
        CreateHarvestDTO harvest = new CreateHarvestDTO(
                "abc",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusMonths(3),
                Collections.emptyList(),
                10L
        );

        Mockito
                .when(millsRepository.findById(10L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                post("/harvests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(harvest))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldReturn200AndList() throws Exception {
        Mockito
                .when(harvestRepository.findAll())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/harvests")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithHarvestsFilteredByStartDate() throws Exception {
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();

        Mockito
                .when(harvestRepository.findAllByStartIsGreaterThanEqual(localDate))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/harvests")
                        .param("start", date)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithHarvestsFilteredByEndDate() throws Exception {
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();

        Mockito
                .when(harvestRepository.findAllByStartIsGreaterThanEqual(localDate))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/harvests")
                        .param("end", date)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithHarvestsFilteredByStartDateBetweenDates() throws Exception {
        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();

        Mockito
                .when(harvestRepository.findAllByStartBetween(localDate, localDate))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/harvests")
                        .param("start", date)
                        .param("end", date)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn200AndHarvestWithId() throws Exception {
        Mockito
                .when(harvestRepository.findById(1L))
                .thenReturn(Optional.of(
                        Harvest.builder().mill(Mill.builder().build()).build()
                ));

        mockMvc
                .perform(get("/harvests/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn404IfNotExistsHarvestWithId() throws Exception {
        Mockito
                .when(harvestRepository.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc
                .perform(get("/harvests/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}
