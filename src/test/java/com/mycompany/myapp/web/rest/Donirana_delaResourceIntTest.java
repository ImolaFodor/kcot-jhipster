package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Donirana_dela;
import com.mycompany.myapp.repository.Donirana_delaRepository;

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
 * Test class for the Donirana_delaResource REST controller.
 *
 * @see Donirana_delaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class Donirana_delaResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAA";
    private static final String UPDATED_NAZIV = "BBBBB";
    private static final String DEFAULT_BR_UGOVORA = "AAAAA";
    private static final String UPDATED_BR_UGOVORA = "BBBBB";

    private static final Long DEFAULT_OPIS = 1L;
    private static final Long UPDATED_OPIS = 2L;

    @Inject
    private Donirana_delaRepository donirana_delaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDonirana_delaMockMvc;

    private Donirana_dela donirana_dela;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Donirana_delaResource donirana_delaResource = new Donirana_delaResource();
        ReflectionTestUtils.setField(donirana_delaResource, "donirana_delaRepository", donirana_delaRepository);
        this.restDonirana_delaMockMvc = MockMvcBuilders.standaloneSetup(donirana_delaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        donirana_dela = new Donirana_dela();
        donirana_dela.setNaziv(DEFAULT_NAZIV);
        donirana_dela.setBr_ugovora(DEFAULT_BR_UGOVORA);
        donirana_dela.setOpis(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void createDonirana_dela() throws Exception {
        int databaseSizeBeforeCreate = donirana_delaRepository.findAll().size();

        // Create the Donirana_dela

        restDonirana_delaMockMvc.perform(post("/api/donirana-delas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(donirana_dela)))
                .andExpect(status().isCreated());

        // Validate the Donirana_dela in the database
        List<Donirana_dela> donirana_delas = donirana_delaRepository.findAll();
        assertThat(donirana_delas).hasSize(databaseSizeBeforeCreate + 1);
        Donirana_dela testDonirana_dela = donirana_delas.get(donirana_delas.size() - 1);
        assertThat(testDonirana_dela.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testDonirana_dela.getBr_ugovora()).isEqualTo(DEFAULT_BR_UGOVORA);
        assertThat(testDonirana_dela.getOpis()).isEqualTo(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void getAllDonirana_delas() throws Exception {
        // Initialize the database
        donirana_delaRepository.saveAndFlush(donirana_dela);

        // Get all the donirana_delas
        restDonirana_delaMockMvc.perform(get("/api/donirana-delas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(donirana_dela.getId().intValue())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].br_ugovora").value(hasItem(DEFAULT_BR_UGOVORA.toString())))
                .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.intValue())));
    }

    @Test
    @Transactional
    public void getDonirana_dela() throws Exception {
        // Initialize the database
        donirana_delaRepository.saveAndFlush(donirana_dela);

        // Get the donirana_dela
        restDonirana_delaMockMvc.perform(get("/api/donirana-delas/{id}", donirana_dela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(donirana_dela.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.br_ugovora").value(DEFAULT_BR_UGOVORA.toString()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDonirana_dela() throws Exception {
        // Get the donirana_dela
        restDonirana_delaMockMvc.perform(get("/api/donirana-delas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonirana_dela() throws Exception {
        // Initialize the database
        donirana_delaRepository.saveAndFlush(donirana_dela);
        int databaseSizeBeforeUpdate = donirana_delaRepository.findAll().size();

        // Update the donirana_dela
        Donirana_dela updatedDonirana_dela = new Donirana_dela();
        updatedDonirana_dela.setId(donirana_dela.getId());
        updatedDonirana_dela.setNaziv(UPDATED_NAZIV);
        updatedDonirana_dela.setBr_ugovora(UPDATED_BR_UGOVORA);
        updatedDonirana_dela.setOpis(UPDATED_OPIS);

        restDonirana_delaMockMvc.perform(put("/api/donirana-delas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDonirana_dela)))
                .andExpect(status().isOk());

        // Validate the Donirana_dela in the database
        List<Donirana_dela> donirana_delas = donirana_delaRepository.findAll();
        assertThat(donirana_delas).hasSize(databaseSizeBeforeUpdate);
        Donirana_dela testDonirana_dela = donirana_delas.get(donirana_delas.size() - 1);
        assertThat(testDonirana_dela.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testDonirana_dela.getBr_ugovora()).isEqualTo(UPDATED_BR_UGOVORA);
        assertThat(testDonirana_dela.getOpis()).isEqualTo(UPDATED_OPIS);
    }

    @Test
    @Transactional
    public void deleteDonirana_dela() throws Exception {
        // Initialize the database
        donirana_delaRepository.saveAndFlush(donirana_dela);
        int databaseSizeBeforeDelete = donirana_delaRepository.findAll().size();

        // Get the donirana_dela
        restDonirana_delaMockMvc.perform(delete("/api/donirana-delas/{id}", donirana_dela.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Donirana_dela> donirana_delas = donirana_delaRepository.findAll();
        assertThat(donirana_delas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
