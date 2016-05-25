package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.RezervisanoSediste;
import com.mycompany.myapp.repository.RezervisanoSedisteRepository;

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
 * Test class for the RezervisanoSedisteResource REST controller.
 *
 * @see RezervisanoSedisteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class RezervisanoSedisteResourceIntTest {


    private static final Long DEFAULT_OPIS = 1L;
    private static final Long UPDATED_OPIS = 2L;

    @Inject
    private RezervisanoSedisteRepository rezervisanoSedisteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRezervisanoSedisteMockMvc;

    private RezervisanoSediste rezervisanoSediste;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RezervisanoSedisteResource rezervisanoSedisteResource = new RezervisanoSedisteResource();
        ReflectionTestUtils.setField(rezervisanoSedisteResource, "rezervisanoSedisteRepository", rezervisanoSedisteRepository);
        this.restRezervisanoSedisteMockMvc = MockMvcBuilders.standaloneSetup(rezervisanoSedisteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rezervisanoSediste = new RezervisanoSediste();
        rezervisanoSediste.setOpis(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void createRezervisanoSediste() throws Exception {
        int databaseSizeBeforeCreate = rezervisanoSedisteRepository.findAll().size();

        // Create the RezervisanoSediste

        restRezervisanoSedisteMockMvc.perform(post("/api/rezervisano-sedistes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rezervisanoSediste)))
                .andExpect(status().isCreated());

        // Validate the RezervisanoSediste in the database
        List<RezervisanoSediste> rezervisanoSedistes = rezervisanoSedisteRepository.findAll();
        assertThat(rezervisanoSedistes).hasSize(databaseSizeBeforeCreate + 1);
        RezervisanoSediste testRezervisanoSediste = rezervisanoSedistes.get(rezervisanoSedistes.size() - 1);
        assertThat(testRezervisanoSediste.getOpis()).isEqualTo(DEFAULT_OPIS);
    }

    @Test
    @Transactional
    public void getAllRezervisanoSedistes() throws Exception {
        // Initialize the database
        rezervisanoSedisteRepository.saveAndFlush(rezervisanoSediste);

        // Get all the rezervisanoSedistes
        restRezervisanoSedisteMockMvc.perform(get("/api/rezervisano-sedistes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rezervisanoSediste.getId().intValue())))
                .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.intValue())));
    }

    @Test
    @Transactional
    public void getRezervisanoSediste() throws Exception {
        // Initialize the database
        rezervisanoSedisteRepository.saveAndFlush(rezervisanoSediste);

        // Get the rezervisanoSediste
        restRezervisanoSedisteMockMvc.perform(get("/api/rezervisano-sedistes/{id}", rezervisanoSediste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rezervisanoSediste.getId().intValue()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRezervisanoSediste() throws Exception {
        // Get the rezervisanoSediste
        restRezervisanoSedisteMockMvc.perform(get("/api/rezervisano-sedistes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRezervisanoSediste() throws Exception {
        // Initialize the database
        rezervisanoSedisteRepository.saveAndFlush(rezervisanoSediste);
        int databaseSizeBeforeUpdate = rezervisanoSedisteRepository.findAll().size();

        // Update the rezervisanoSediste
        RezervisanoSediste updatedRezervisanoSediste = new RezervisanoSediste();
        updatedRezervisanoSediste.setId(rezervisanoSediste.getId());
        updatedRezervisanoSediste.setOpis(UPDATED_OPIS);

        restRezervisanoSedisteMockMvc.perform(put("/api/rezervisano-sedistes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRezervisanoSediste)))
                .andExpect(status().isOk());

        // Validate the RezervisanoSediste in the database
        List<RezervisanoSediste> rezervisanoSedistes = rezervisanoSedisteRepository.findAll();
        assertThat(rezervisanoSedistes).hasSize(databaseSizeBeforeUpdate);
        RezervisanoSediste testRezervisanoSediste = rezervisanoSedistes.get(rezervisanoSedistes.size() - 1);
        assertThat(testRezervisanoSediste.getOpis()).isEqualTo(UPDATED_OPIS);
    }

    @Test
    @Transactional
    public void deleteRezervisanoSediste() throws Exception {
        // Initialize the database
        rezervisanoSedisteRepository.saveAndFlush(rezervisanoSediste);
        int databaseSizeBeforeDelete = rezervisanoSedisteRepository.findAll().size();

        // Get the rezervisanoSediste
        restRezervisanoSedisteMockMvc.perform(delete("/api/rezervisano-sedistes/{id}", rezervisanoSediste.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RezervisanoSediste> rezervisanoSedistes = rezervisanoSedisteRepository.findAll();
        assertThat(rezervisanoSedistes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
