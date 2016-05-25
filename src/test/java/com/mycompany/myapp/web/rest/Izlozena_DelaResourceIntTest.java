package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Izlozena_dela;
import com.mycompany.myapp.repository.Izlozena_DelaRepository;

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
 * Test class for the Izlozena_delaResource REST controller.
 *
 * @see Izlozena_delaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class Izlozena_delaResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAA";
    private static final String UPDATED_NAZIV = "BBBBB";

    private static final Long DEFAULT_OPIS = 1L;
    private static final Long UPDATED_OPIS = 2L;

    @Inject
    private Izlozena_DelaRepository izlozena_delaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIzlozena_delaMockMvc;

    private Izlozena_dela izlozena_dela;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Izlozena_delaResource izlozena_delaResource = new Izlozena_delaResource();
        ReflectionTestUtils.setField(izlozena_delaResource, "izlozena_delaRepository", izlozena_delaRepository);
        this.restIzlozena_delaMockMvc = MockMvcBuilders.standaloneSetup(izlozena_delaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        izlozena_dela = new Izlozena_dela();
        izlozena_dela.setNaziv(DEFAULT_NAZIV);
        izlozena_dela.setOpis(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void createIzlozena_dela() throws Exception {
        int databaseSizeBeforeCreate = izlozena_delaRepository.findAll().size();

        // Create the Izlozena_dela

        restIzlozena_delaMockMvc.perform(post("/api/izlozena-delas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(izlozena_dela)))
                .andExpect(status().isCreated());

        // Validate the Izlozena_dela in the database
        List<Izlozena_dela> izlozena_delas = izlozena_delaRepository.findAll();
        assertThat(izlozena_delas).hasSize(databaseSizeBeforeCreate + 1);
        Izlozena_dela testIzlozena_dela = izlozena_delas.get(izlozena_delas.size() - 1);
        assertThat(testIzlozena_dela.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testIzlozena_dela.getOpis()).isEqualTo(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void getAllIzlozena_delas() throws Exception {
        // Initialize the database
        izlozena_delaRepository.saveAndFlush(izlozena_dela);

        // Get all the izlozena_delas
        restIzlozena_delaMockMvc.perform(get("/api/izlozena-delas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(izlozena_dela.getId().intValue())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.intValue())));
    }

    @Test
    @Transactional
    public void getIzlozena_dela() throws Exception {
        // Initialize the database
        izlozena_delaRepository.saveAndFlush(izlozena_dela);

        // Get the izlozena_dela
        restIzlozena_delaMockMvc.perform(get("/api/izlozena-delas/{id}", izlozena_dela.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(izlozena_dela.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIzlozena_dela() throws Exception {
        // Get the izlozena_dela
        restIzlozena_delaMockMvc.perform(get("/api/izlozena-delas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIzlozena_dela() throws Exception {
        // Initialize the database
        izlozena_delaRepository.saveAndFlush(izlozena_dela);
        int databaseSizeBeforeUpdate = izlozena_delaRepository.findAll().size();

        // Update the izlozena_dela
        Izlozena_dela updatedIzlozena_dela = new Izlozena_dela();
        updatedIzlozena_dela.setId(izlozena_dela.getId());
        updatedIzlozena_dela.setNaziv(UPDATED_NAZIV);
        updatedIzlozena_dela.setOpis(UPDATED_OPIS);

        restIzlozena_delaMockMvc.perform(put("/api/izlozena-delas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedIzlozena_dela)))
                .andExpect(status().isOk());

        // Validate the Izlozena_dela in the database
        List<Izlozena_dela> izlozena_delas = izlozena_delaRepository.findAll();
        assertThat(izlozena_delas).hasSize(databaseSizeBeforeUpdate);
        Izlozena_dela testIzlozena_dela = izlozena_delas.get(izlozena_delas.size() - 1);
        assertThat(testIzlozena_dela.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testIzlozena_dela.getOpis()).isEqualTo(UPDATED_OPIS);
    }

    @Test
    @Transactional
    public void deleteIzlozena_dela() throws Exception {
        // Initialize the database
        izlozena_delaRepository.saveAndFlush(izlozena_dela);
        int databaseSizeBeforeDelete = izlozena_delaRepository.findAll().size();

        // Get the izlozena_dela
        restIzlozena_delaMockMvc.perform(delete("/api/izlozena-delas/{id}", izlozena_dela.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Izlozena_dela> izlozena_delas = izlozena_delaRepository.findAll();
        assertThat(izlozena_delas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
