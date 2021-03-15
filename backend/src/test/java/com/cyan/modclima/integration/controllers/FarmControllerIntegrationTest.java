package com.cyan.modclima.integration.controllers;

import com.cyan.modclima.dtos.CreateFarmDTO;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.FarmRepository;
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
public class FarmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private MillsRepository millsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Harvest harvest;

    @BeforeEach
    public void setup() {
        Mill mill = millsRepository.save(
                Mill.builder().name("mill").build()
        );

        this.harvest = harvestRepository.save(
                Harvest
                        .builder()
                        .start(LocalDate.now())
                        .end(LocalDate.now().plusMonths(6).plusDays(10))
                        .code("abcdefg")
                        .mill(mill)
                        .build()
        );

        String[] names = {"farm 1", "farm 2", "farm 3", "farm 4", "farm 5",
                "farm 6", "farm 7", "farm 8", "farm 9", "farm 10"};

        for (String name : names) {
            farmRepository.save(
                    Farm
                            .builder()
                            .harvest(harvest)
                            .name(name)
                            .code(name + "-" + name.hashCode())
                            .build()
            );
        }
    }

    @AfterEach
    public void tearDown() {
        farmRepository.deleteAll();
        harvestRepository.deleteAll();
        millsRepository.deleteAll();
    }

    @Test
    public void itShouldBeCreateAFarmSuccessfully() throws Exception {
        CreateFarmDTO createFarmDTO = new CreateFarmDTO("abc", "farm x", new ArrayList<>(), harvest.getId());

        MvcResult result = mockMvc
                .perform(
                        post("/farms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFarmDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        Farm farm = objectMapper.readValue(result.getResponse().getContentAsString(), Farm.class);

        assertEquals(farm.getName(), "farm x");
    }

    @Test
    public void itShouldBeFailCreatingAFarmWhenHarvestIdNotExists() throws Exception {
        CreateFarmDTO createFarmDTO = new CreateFarmDTO("abc", "farm x", new ArrayList<>(), 1000L);

        mockMvc
                .perform(
                        post("/farms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFarmDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeFailCreatingAFarmWhenCodeIsEmpty() throws Exception {
        CreateFarmDTO createFarmDTO = new CreateFarmDTO("", "farm x", new ArrayList<>(), 1000L);

        mockMvc
                .perform(
                        post("/farms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFarmDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeFailCreatingAFarmWhenNameIsEmpty() throws Exception {
        CreateFarmDTO createFarmDTO = new CreateFarmDTO("abc", "", new ArrayList<>(), 1000L);

        mockMvc
                .perform(
                        post("/farms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFarmDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeReturnAListOfFarms() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/farms")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 10);
    }

    @Test
    public void itShouldBeReturnAListOfFarmsWithNameMatching() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/farms")
                                .param("name", "4")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 1);
    }

    @Test
    public void itShouldBeReturnAListOfFarmsWithCodeMatching() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/farms")
                                .param("code", "fARm 6")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 1);
    }

    @Test
    public void itShouldBeReturnAFarmWithId() throws Exception {
        List<Farm> all = farmRepository.findAll();
        Random random = new Random();

        Farm find = all.get(Math.round(random.ints(0, all.size()).findAny().getAsInt()));

        MvcResult result = mockMvc
                .perform(
                        get("/farms/{id}", find.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Farm farm = objectMapper.readValue(result.getResponse().getContentAsString(), Farm.class);

        assertEquals(farm.getId(), find.getId());
        assertEquals(farm.getName(), find.getName());
        assertEquals(farm.getCode(), find.getCode());
    }

    @Test
    public void itShouldBeReturn400WithFarmNotFound() throws Exception {
        mockMvc
                .perform(
                        get("/farms/{id}", -8L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

}
