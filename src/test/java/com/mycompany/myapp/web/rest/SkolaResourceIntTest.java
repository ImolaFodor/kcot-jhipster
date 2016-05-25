package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Skola;
import com.mycompany.myapp.repository.SkolaRepository;

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
 * Test class for the SkolaResource REST controller.
 *
 * @see SkolaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class SkolaResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAA";
    private static final String UPDATED_NAZIV = "BBBBB";
    private static final String DEFAULT_KONTAKT_IME = "AAAAA";
    private static final String UPDATED_KONTAKT_IME = "BBBBB";
    private static final String DEFAULT_KONTAKT_PRZ = "AAAAA";
    private static final String UPDATED_KONTAKT_PRZ = "BBBBB";
    private static final String DEFAULT_KONTAKT_BROJ = "AAAAA";
    private static final String UPDATED_KONTAKT_BROJ = "BBBBB";

    @Inject
    private SkolaRepository skolaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSkolaMockMvc;

    private Skola skola;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SkolaResource skolaResource = new SkolaResource();
        ReflectionTestUtils.setField(skolaResource, "skolaRepository", skolaRepository);
        this.restSkolaMockMvc = MockMvcBuilders.standaloneSetup(skolaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        skola = new Skola();
        skola.setNaziv(DEFAULT_NAZIV);
        skola.setKontakt_ime(DEFAULT_KONTAKT_IME);
        skola.setKontakt_prz(DEFAULT_KONTAKT_PRZ);
        skola.setKontakt_broj(DEFAULT_KONTAKT_BROJ);
    }

    @Test
    @Transactional
    public void createSkola() throws Exception {
        int databaseSizeBeforeCreate = skolaRepository.findAll().size();

        // Create the Skola

        restSkolaMockMvc.perform(post("/api/skolas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skola)))
                .andExpect(status().isCreated());

        // Validate the Skola in the database
        List<Skola> skolas = skolaRepository.findAll();
        assertThat(skolas).hasSize(databaseSizeBeforeCreate + 1);
        Skola testSkola = skolas.get(skolas.size() - 1);
        assertThat(testSkola.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testSkola.getKontakt_ime()).isEqualTo(DEFAULT_KONTAKT_IME);
        assertThat(testSkola.getKontakt_prz()).isEqualTo(DEFAULT_KONTAKT_PRZ);
        assertThat(testSkola.getKontakt_broj()).isEqualTo(DEFAULT_KONTAKT_BROJ);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = skolaRepository.findAll().size();
        // set the field null
        skola.setNaziv(null);

        // Create the Skola, which fails.

        restSkolaMockMvc.perform(post("/api/skolas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(skola)))
                .andExpect(status().isBadRequest());

        List<Skola> skolas = skolaRepository.findAll();
        assertThat(skolas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkolas() throws Exception {
        // Initialize the database
        skolaRepository.saveAndFlush(skola);

        // Get all the skolas
        restSkolaMockMvc.perform(get("/api/skolas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(skola.getId().intValue())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].kontakt_ime").value(hasItem(DEFAULT_KONTAKT_IME.toString())))
                .andExpect(jsonPath("$.[*].kontakt_prz").value(hasItem(DEFAULT_KONTAKT_PRZ.toString())))
                .andExpect(jsonPath("$.[*].kontakt_broj").value(hasItem(DEFAULT_KONTAKT_BROJ.toString())));
    }

    @Test
    @Transactional
    public void getSkola() throws Exception {
        // Initialize the database
        skolaRepository.saveAndFlush(skola);

        // Get the skola
        restSkolaMockMvc.perform(get("/api/skolas/{id}", skola.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(skola.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.kontakt_ime").value(DEFAULT_KONTAKT_IME.toString()))
            .andExpect(jsonPath("$.kontakt_prz").value(DEFAULT_KONTAKT_PRZ.toString()))
            .andExpect(jsonPath("$.kontakt_broj").value(DEFAULT_KONTAKT_BROJ.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkola() throws Exception {
        // Get the skola
        restSkolaMockMvc.perform(get("/api/skolas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkola() throws Exception {
        // Initialize the database
        skolaRepository.saveAndFlush(skola);
        int databaseSizeBeforeUpdate = skolaRepository.findAll().size();

        // Update the skola
        Skola updatedSkola = new Skola();
        updatedSkola.setId(skola.getId());
        updatedSkola.setNaziv(UPDATED_NAZIV);
        updatedSkola.setKontakt_ime(UPDATED_KONTAKT_IME);
        updatedSkola.setKontakt_prz(UPDATED_KONTAKT_PRZ);
        updatedSkola.setKontakt_broj(UPDATED_KONTAKT_BROJ);

        restSkolaMockMvc.perform(put("/api/skolas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSkola)))
                .andExpect(status().isOk());

        // Validate the Skola in the database
        List<Skola> skolas = skolaRepository.findAll();
        assertThat(skolas).hasSize(databaseSizeBeforeUpdate);
        Skola testSkola = skolas.get(skolas.size() - 1);
        assertThat(testSkola.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testSkola.getKontakt_ime()).isEqualTo(UPDATED_KONTAKT_IME);
        assertThat(testSkola.getKontakt_prz()).isEqualTo(UPDATED_KONTAKT_PRZ);
        assertThat(testSkola.getKontakt_broj()).isEqualTo(UPDATED_KONTAKT_BROJ);
    }

    @Test
    @Transactional
    public void deleteSkola() throws Exception {
        // Initialize the database
        skolaRepository.saveAndFlush(skola);
        int databaseSizeBeforeDelete = skolaRepository.findAll().size();

        // Get the skola
        restSkolaMockMvc.perform(delete("/api/skolas/{id}", skola.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Skola> skolas = skolaRepository.findAll();
        assertThat(skolas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
