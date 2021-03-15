package com.cyan.modclima.integration.controllers;

import com.cyan.modclima.dtos.CreateFieldDTO;
import com.cyan.modclima.models.Farm;
import com.cyan.modclima.models.Field;
import com.cyan.modclima.models.Harvest;
import com.cyan.modclima.models.Mill;
import com.cyan.modclima.repositories.FarmRepository;
import com.cyan.modclima.repositories.FieldRepository;
import com.cyan.modclima.repositories.HarvestRepository;
import com.cyan.modclima.repositories.MillsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FieldControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private MillsRepository millsRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Farm farm;

    private GeometryFactory factory = new GeometryFactory();

    @BeforeEach
    public void setup() {
        Mill mill = millsRepository.save(
                Mill.builder().name("mill").build()
        );

        Harvest harvest = harvestRepository.save(
                Harvest
                        .builder()
                        .start(LocalDate.now())
                        .end(LocalDate.now().plusMonths(6).plusDays(10))
                        .code("abcdefg")
                        .mill(mill)
                        .build()
        );

        this.farm = farmRepository.save(
                Farm
                        .builder()
                        .harvest(harvest)
                        .name("farm")
                        .code("farm" + "-" + "farm".hashCode())
                        .build()
        );

        String[] codes = {"field 1", "field 2", "field 3", "field 4", "field 5",
                "field 6", "field 7", "field 8", "field 9", "field 10"};

        for (String code : codes) {
            fieldRepository.save(
                    Field
                            .builder()
                            .farm(farm)
                            .code(code)
                            .geom(this.factory.createPoint(new Coordinate(-7.56516516, 30.5445544)))
                            .build()
            );
        }
    }

    @AfterEach
    public void tearDown() {
        fieldRepository.deleteAll();
        farmRepository.deleteAll();
        harvestRepository.deleteAll();
        millsRepository.deleteAll();
    }

    @Test
    public void itShouldBeCreateAFieldSuccessfully() throws Exception {
        CreateFieldDTO createFieldDTO = new CreateFieldDTO(
                "abc",
                this.factory.createPoint(new Coordinate(-7.56516516, 30.5445544)),
                farm.getId()
        );

        MvcResult result = mockMvc
                .perform(
                        post("/fields")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFieldDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        Field field = objectMapper.readValue(result.getResponse().getContentAsString(), Field.class);

        assertEquals(field.getCode(), "abc");
    }

    @Test
    public void itShouldBeFailCreatingAFieldWhenCodeIsEmpty() throws Exception {
        CreateFieldDTO createFieldDTO = new CreateFieldDTO(
                "",
                this.factory.createPoint(new Coordinate(-7.56516516, 30.5445544)),
                farm.getId()
        );

        mockMvc
                .perform(
                        post("/fields")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFieldDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeFailCreatingAFieldWhenFarmIdNotExists() throws Exception {
        CreateFieldDTO createFieldDTO = new CreateFieldDTO(
                "",
                this.factory.createPoint(new Coordinate(-7.56516516, 30.5445544)),
                100L
        );

        mockMvc
                .perform(
                        post("/fields")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createFieldDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itShouldBeReturnAListOfFields() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/fields")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 10);
    }

    @Test
    public void itShouldBeReturnAListOfFieldsWithCodeMatching() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/fields")
                                .param("code", "FiElD 7")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        assertEquals(list.size(), 1);
    }

    @Test
    public void itShouldBeReturnAFieldWithId() throws Exception {
        List<Field> all = fieldRepository.findAll();
        Random random = new Random();

        Field find = all.get(Math.round(random.ints(0, all.size()).findAny().getAsInt()));

        MvcResult result = mockMvc
                .perform(
                        get("/fields/{id}", find.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Farm farm = objectMapper.readValue(result.getResponse().getContentAsString(), Farm.class);

        assertEquals(farm.getId(), find.getId());
        assertEquals(farm.getCode(), find.getCode());
    }

    @Test
    public void itShouldBeReturn400WithFieldNotFound() throws Exception {
        mockMvc
                .perform(
                        get("/fields/{id}", -8L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

}
