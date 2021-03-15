package com.cyan.modclima.integration.controllers;

import com.cyan.modclima.dtos.CreateHarvestDTO;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.HarvestRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HarvestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private MillsRepository millsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Mill mill;

    @BeforeEach
    public void setup() {
        this.mill = millsRepository.save(
                Mill.builder().name("mill").build()
        );

        String[] names = {"harvest 1", "harvest 2", "harvest 3", "harvest 4", "harvest 5",
                "harvest 6", "harvest 7", "harvest 8", "harvest 9", "harvest 10"};

        for (String name : names) {
            harvestRepository.save(
                    Harvest
                            .builder()
                            .mill(this.mill)
                            .start(LocalDate.now())
                            .end(LocalDate.now().plusMonths(6).plusDays(15))
                            .code(name + "-" + name.hashCode())
                            .build()
            );
        }
    }

    @AfterEach
    public void tearDown() {
        harvestRepository.deleteAll();
        millsRepository.deleteAll();
    }

    @Test
    public void itShouldBeCreateAHarvestSuccessfully() throws Exception {
        CreateHarvestDTO createHarvestDTO = new CreateHarvestDTO(
                "abc",
                LocalDate.now(),
                LocalDate.now().plusDays(2).plusMonths(2),
                new ArrayList<>(),
                this.mill.getId()
        );

        MvcResult result = mockMvc
                .perform(
                        post("/harvests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createHarvestDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        Harvest harvest = objectMapper.readValue(result.getResponse().getContentAsString(), Harvest.class);

        assertEquals(harvest.getCode(), "abc");
    }

    @Test
    public void itShouldBeFailCreatingAFarmWhenMillIdNotExists() throws Exception {
        CreateHarvestDTO createHarvestDTO = new CreateHarvestDTO(
                "abc",
                LocalDate.now(),
                LocalDate.now().plusDays(2).plusMonths(2),
                new ArrayList<>(),
                1000L
        );

        mockMvc
                .perform(
                        post("/farms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createHarvestDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeFailCreatingAHarvestWhenCodeIsEmpty() throws Exception {
        CreateHarvestDTO createHarvestDTO = new CreateHarvestDTO(
                "",
                LocalDate.now(),
                LocalDate.now().plusDays(2).plusMonths(2),
                new ArrayList<>(),
                this.mill.getId()
        );

        mockMvc
                .perform(
                        post("/harvests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createHarvestDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeReturnAListOfHarvests() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/harvests")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 10);
    }

    @Test
    public void itShouldReturn200AndListWithHarvestsFilteredByStartDate() throws Exception {
        String date = LocalDate.now().toString();

        MvcResult result = mockMvc
                .perform(
                        get("/harvests")
                                .param("start", date)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 10);
    }

    @Test
    public void itShouldReturn200AndListWithHarvestsFilteredByEndDate() throws Exception {
        String date = LocalDate.now().toString();

        MvcResult result = mockMvc
                .perform(
                        get("/harvests")
                                .param("end", date)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 0);
    }

    @Test
    public void itShouldReturn200AndListWithHarvestsFilteredByStartDateBetweenDates() throws Exception {
        String date = LocalDate.now().toString();

        MvcResult result = mockMvc
                .perform(
                        get("/harvests")
                                .param("start", date)
                                .param("end", LocalDate.now().plusMonths(2).toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 10);
    }

    @Test
    public void itShouldBeReturnAHarvestWithId() throws Exception {
        List<Harvest> all = harvestRepository.findAll();
        Random random = new Random();

        Harvest find = all.get(Math.round(random.ints(0, all.size()).findAny().getAsInt()));

        MvcResult result = mockMvc
                .perform(
                        get("/harvests/{id}", find.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Harvest harvest = objectMapper.readValue(result.getResponse().getContentAsString(), Harvest.class);

        assertEquals(harvest.getId(), find.getId());
        assertEquals(harvest.getCode(), find.getCode());
    }

    @Test
    public void itShouldBeReturn400WithHarvestNotFound() throws Exception {
        mockMvc
                .perform(
                        get("/harvests/{id}", -8L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

}
