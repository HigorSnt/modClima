package com.cyan.modclima.integration.controllers;

import com.cyan.modclima.dtos.MillDTO;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.MillsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MillsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MillsRepository millsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        String[] names = {"mill 1", "mill 2", "mill 3", "mill 4", "mill 5",
                "mill 6", "mill 7", "mill 8", "mill 9", "mill 10"};

        for (String name : names) {
            millsRepository.save(
                    Mill.builder().name(name).build()
            );
        }
    }

    @AfterEach
    public void tearDown() {
        millsRepository.deleteAll();
    }

    @Test
    public void itShouldSuccessfullyCreateAMill() throws Exception {
        MillDTO mill = new MillDTO(1L, "mill", new ArrayList<>());

        MvcResult result = mockMvc
                .perform(
                        post("/mills")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mill))
                )
                .andExpect(status().isCreated())
                .andReturn();

        Mill mill1 = objectMapper.readValue(result.getResponse().getContentAsString(), Mill.class);

        assertEquals(mill1.getName(), "mill");
    }

    @Test
    public void itShouldBeFailCreatingAMill() throws Exception {
        MillDTO mill = new MillDTO(1L, "", new ArrayList<>());

        mockMvc
                .perform(
                        post("/mills")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mill))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeReturnAListOfMills() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/mills")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 10);
    }

    @Test
    public void itShouldBeReturnAListOfMillsWithNameMatching() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/mills")
                                .param("name", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 1);
    }

    @Test
    public void itShouldBeReturnAMillWithId() throws Exception {
        List<Mill> all = millsRepository.findAll();
        Random random = new Random();

        Mill find = all.get(Math.round(random.ints(0, all.size()).findAny().getAsInt()));

        MvcResult result = mockMvc
                .perform(
                        get("/mills/{id}", find.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Mill mill = objectMapper.readValue(result.getResponse().getContentAsString(), Mill.class);

        assertEquals(mill.getId(), find.getId());
        assertEquals(mill.getName(), find.getName());
    }

    @Test
    public void itShouldBeReturn400WithMillNotFound() throws Exception {
        mockMvc
                .perform(
                        get("/mills/{id}", -8L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

}
