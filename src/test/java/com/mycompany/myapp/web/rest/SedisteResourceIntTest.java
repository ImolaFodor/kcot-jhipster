package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Sediste;
import com.mycompany.myapp.repository.SedisteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SedisteResource REST controller.
 *
 * @see SedisteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class SedisteResourceIntTest {


    private static final Integer DEFAULT_RED = 1;
    private static final Integer UPDATED_RED = 2;

    private static final Integer DEFAULT_BROJ = 1;
    private static final Integer UPDATED_BROJ = 2;

    @Inject
    private SedisteRepository sedisteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSedisteMockMvc;

    private Sediste sediste;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SedisteResource sedisteResource = new SedisteResource();
        ReflectionTestUtils.setField(sedisteResource, "sedisteRepository", sedisteRepository);
        this.restSedisteMockMvc = MockMvcBuilders.standaloneSetup(sedisteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        sediste = new Sediste();
        sediste.setRed(DEFAULT_RED);
        sediste.setBroj(DEFAULT_BROJ);
    }

    @Test
    @Transactional
    public void createSediste() throws Exception {
        int databaseSizeBeforeCreate = sedisteRepository.findAll().size();

        // Create the Sediste

        restSedisteMockMvc.perform(post("/api/sedistes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sediste)))
                .andExpect(status().isCreated());

        // Validate the Sediste in the database
        List<Sediste> sedistes = sedisteRepository.findAll();
        assertThat(sedistes).hasSize(databaseSizeBeforeCreate + 1);
        Sediste testSediste = sedistes.get(sedistes.size() - 1);
        assertThat(testSediste.getRed()).isEqualTo(DEFAULT_RED);
        assertThat(testSediste.getBroj()).isEqualTo(DEFAULT_BROJ);
    }

    @Test
    @Transactional
    public void checkRedIsRequired() throws Exception {
        int databaseSizeBeforeTest = sedisteRepository.findAll().size();
        // set the field null
        sediste.setRed(null);

        // Create the Sediste, which fails.

        restSedisteMockMvc.perform(post("/api/sedistes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sediste)))
                .andExpect(status().isBadRequest());

        List<Sediste> sedistes = sedisteRepository.findAll();
        assertThat(sedistes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrojIsRequired() throws Exception {
        int databaseSizeBeforeTest = sedisteRepository.findAll().size();
        // set the field null
        sediste.setBroj(null);

        // Create the Sediste, which fails.

        restSedisteMockMvc.perform(post("/api/sedistes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sediste)))
                .andExpect(status().isBadRequest());

        List<Sediste> sedistes = sedisteRepository.findAll();
        assertThat(sedistes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSedistes() throws Exception {
        // Initialize the database
        sedisteRepository.saveAndFlush(sediste);

        // Get all the sedistes
        restSedisteMockMvc.perform(get("/api/sedistes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sediste.getId().intValue())))
                .andExpect(jsonPath("$.[*].red").value(hasItem(DEFAULT_RED)))
                .andExpect(jsonPath("$.[*].broj").value(hasItem(DEFAULT_BROJ)));
    }

    @Test
    @Transactional
    public void getSediste() throws Exception {
        // Initialize the database
        sedisteRepository.saveAndFlush(sediste);

        // Get the sediste
        restSedisteMockMvc.perform(get("/api/sedistes/{id}", sediste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sediste.getId().intValue()))
            .andExpect(jsonPath("$.red").value(DEFAULT_RED))
            .andExpect(jsonPath("$.broj").value(DEFAULT_BROJ));
    }

    @Test
    @Transactional
    public void getNonExistingSediste() throws Exception {
        // Get the sediste
        restSedisteMockMvc.perform(get("/api/sedistes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSediste() throws Exception {
        // Initialize the database
        sedisteRepository.saveAndFlush(sediste);
        int databaseSizeBeforeUpdate = sedisteRepository.findAll().size();

        // Update the sediste
        Sediste updatedSediste = new Sediste();
        updatedSediste.setId(sediste.getId());
        updatedSediste.setRed(UPDATED_RED);
        updatedSediste.setBroj(UPDATED_BROJ);

        restSedisteMockMvc.perform(put("/api/sedistes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSediste)))
                .andExpect(status().isOk());

        // Validate the Sediste in the database
        List<Sediste> sedistes = sedisteRepository.findAll();
        assertThat(sedistes).hasSize(databaseSizeBeforeUpdate);
        Sediste testSediste = sedistes.get(sedistes.size() - 1);
        assertThat(testSediste.getRed()).isEqualTo(UPDATED_RED);
        assertThat(testSediste.getBroj()).isEqualTo(UPDATED_BROJ);
    }

    @Test
    @Transactional
    public void deleteSediste() throws Exception {
        // Initialize the database
        sedisteRepository.saveAndFlush(sediste);
        int databaseSizeBeforeDelete = sedisteRepository.findAll().size();

        // Get the sediste
        restSedisteMockMvc.perform(delete("/api/sedistes/{id}", sediste.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Sediste> sedistes = sedisteRepository.findAll();
        assertThat(sedistes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
