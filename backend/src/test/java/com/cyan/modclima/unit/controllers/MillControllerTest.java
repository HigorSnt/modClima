package com.cyan.modclima.unit.controllers;

import com.cyan.modclima.dtos.MillDTO;
import com.cyan.modclima.models.Mill;
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

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MillsRepository millsRepository;

    @Test
    public void itShouldReturn201WhenMillIsCreated() throws Exception {
        MillDTO mill = new MillDTO(1L, "mill", Collections.emptyList());

        Mockito.when(millsRepository.save(any())).thenReturn(
                Mill.builder().build()
        );

        mockMvc.perform(
                post("/mills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mill))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void itShouldReturn200AndList() throws Exception {
        Mockito
                .when(millsRepository.findAllByNameContainingIgnoreCase(any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/mills")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturn200AndListWithMillsFilteredByName() throws Exception {
        Mockito
                .when(millsRepository.findAllByNameContainingIgnoreCase("mill"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(
                get("/mills")
                        .param("name", "mill")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn200AndMillWithId() throws Exception {
        Mockito
                .when(millsRepository.findById(1L))
                .thenReturn(Optional.of(Mill.builder().build()));

        mockMvc
                .perform(get("/mills/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldBeReturn404IfNotExistsMillWithId() throws Exception {
        Mockito
                .when(millsRepository.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc
                .perform(get("/mills/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}
