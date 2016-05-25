package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Pozvani;
import com.mycompany.myapp.repository.PozvaniRepository;

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

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * Test class for the PozvaniResource REST controller.
 *
 * @see PozvaniResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class PozvaniResourceIntTest {


    private static final Status DEFAULT_STATUS = Status.POTVRDIO;
    private static final Status UPDATED_STATUS = Status.ODBIO;

    @Inject
    private PozvaniRepository pozvaniRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPozvaniMockMvc;

    private Pozvani pozvani;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PozvaniResource pozvaniResource = new PozvaniResource();
        ReflectionTestUtils.setField(pozvaniResource, "pozvaniRepository", pozvaniRepository);
        this.restPozvaniMockMvc = MockMvcBuilders.standaloneSetup(pozvaniResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pozvani = new Pozvani();
        pozvani.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPozvani() throws Exception {
        int databaseSizeBeforeCreate = pozvaniRepository.findAll().size();

        // Create the Pozvani

        restPozvaniMockMvc.perform(post("/api/pozvanis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pozvani)))
                .andExpect(status().isCreated());

        // Validate the Pozvani in the database
        List<Pozvani> pozvanis = pozvaniRepository.findAll();
        assertThat(pozvanis).hasSize(databaseSizeBeforeCreate + 1);
        Pozvani testPozvani = pozvanis.get(pozvanis.size() - 1);
        assertThat(testPozvani.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllPozvanis() throws Exception {
        // Initialize the database
        pozvaniRepository.saveAndFlush(pozvani);

        // Get all the pozvanis
        restPozvaniMockMvc.perform(get("/api/pozvanis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pozvani.getId().intValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPozvani() throws Exception {
        // Initialize the database
        pozvaniRepository.saveAndFlush(pozvani);

        // Get the pozvani
        restPozvaniMockMvc.perform(get("/api/pozvanis/{id}", pozvani.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pozvani.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPozvani() throws Exception {
        // Get the pozvani
        restPozvaniMockMvc.perform(get("/api/pozvanis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePozvani() throws Exception {
        // Initialize the database
        pozvaniRepository.saveAndFlush(pozvani);
        int databaseSizeBeforeUpdate = pozvaniRepository.findAll().size();

        // Update the pozvani
        Pozvani updatedPozvani = new Pozvani();
        updatedPozvani.setId(pozvani.getId());
        updatedPozvani.setStatus(UPDATED_STATUS);

        restPozvaniMockMvc.perform(put("/api/pozvanis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPozvani)))
                .andExpect(status().isOk());

        // Validate the Pozvani in the database
        List<Pozvani> pozvanis = pozvaniRepository.findAll();
        assertThat(pozvanis).hasSize(databaseSizeBeforeUpdate);
        Pozvani testPozvani = pozvanis.get(pozvanis.size() - 1);
        assertThat(testPozvani.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deletePozvani() throws Exception {
        // Initialize the database
        pozvaniRepository.saveAndFlush(pozvani);
        int databaseSizeBeforeDelete = pozvaniRepository.findAll().size();

        // Get the pozvani
        restPozvaniMockMvc.perform(delete("/api/pozvanis/{id}", pozvani.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pozvani> pozvanis = pozvaniRepository.findAll();
        assertThat(pozvanis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
