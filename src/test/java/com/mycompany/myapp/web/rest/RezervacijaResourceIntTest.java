package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Rezervacija;
import com.mycompany.myapp.repository.RezervacijaRepository;

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
 * Test class for the RezervacijaResource REST controller.
 *
 * @see RezervacijaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class RezervacijaResourceIntTest {

    private static final String DEFAULT_UPS = "AAAAA";
    private static final String UPDATED_UPS = "BBBBB";

    @Inject
    private RezervacijaRepository rezervacijaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRezervacijaMockMvc;

    private Rezervacija rezervacija;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RezervacijaResource rezervacijaResource = new RezervacijaResource();
        ReflectionTestUtils.setField(rezervacijaResource, "rezervacijaRepository", rezervacijaRepository);
        this.restRezervacijaMockMvc = MockMvcBuilders.standaloneSetup(rezervacijaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rezervacija = new Rezervacija();
        rezervacija.setUps(DEFAULT_UPS);
    }

    @Test
    @Transactional
    public void createRezervacija() throws Exception {
        int databaseSizeBeforeCreate = rezervacijaRepository.findAll().size();

        // Create the Rezervacija

        restRezervacijaMockMvc.perform(post("/api/rezervacijas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rezervacija)))
                .andExpect(status().isCreated());

        // Validate the Rezervacija in the database
        List<Rezervacija> rezervacijas = rezervacijaRepository.findAll();
        assertThat(rezervacijas).hasSize(databaseSizeBeforeCreate + 1);
        Rezervacija testRezervacija = rezervacijas.get(rezervacijas.size() - 1);
        assertThat(testRezervacija.getUps()).isEqualTo(DEFAULT_UPS);
    }

    @Test
    @Transactional
    public void getAllRezervacijas() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);

        // Get all the rezervacijas
        restRezervacijaMockMvc.perform(get("/api/rezervacijas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rezervacija.getId().intValue())))
                .andExpect(jsonPath("$.[*].ups").value(hasItem(DEFAULT_UPS.toString())));
    }

    @Test
    @Transactional
    public void getRezervacija() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);

        // Get the rezervacija
        restRezervacijaMockMvc.perform(get("/api/rezervacijas/{id}", rezervacija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rezervacija.getId().intValue()))
            .andExpect(jsonPath("$.ups").value(DEFAULT_UPS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRezervacija() throws Exception {
        // Get the rezervacija
        restRezervacijaMockMvc.perform(get("/api/rezervacijas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRezervacija() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);
        int databaseSizeBeforeUpdate = rezervacijaRepository.findAll().size();

        // Update the rezervacija
        Rezervacija updatedRezervacija = new Rezervacija();
        updatedRezervacija.setId(rezervacija.getId());
        updatedRezervacija.setUps(UPDATED_UPS);

        restRezervacijaMockMvc.perform(put("/api/rezervacijas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRezervacija)))
                .andExpect(status().isOk());

        // Validate the Rezervacija in the database
        List<Rezervacija> rezervacijas = rezervacijaRepository.findAll();
        assertThat(rezervacijas).hasSize(databaseSizeBeforeUpdate);
        Rezervacija testRezervacija = rezervacijas.get(rezervacijas.size() - 1);
        assertThat(testRezervacija.getUps()).isEqualTo(UPDATED_UPS);
    }

    @Test
    @Transactional
    public void deleteRezervacija() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);
        int databaseSizeBeforeDelete = rezervacijaRepository.findAll().size();

        // Get the rezervacija
        restRezervacijaMockMvc.perform(delete("/api/rezervacijas/{id}", rezervacija.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rezervacija> rezervacijas = rezervacijaRepository.findAll();
        assertThat(rezervacijas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
