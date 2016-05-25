package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.DnevniPazar;
import com.mycompany.myapp.repository.DnevniPazarRepository;

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
 * Test class for the DnevniPazarResource REST controller.
 *
 * @see DnevniPazarResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class DnevniPazarResourceIntTest {


    private static final Integer DEFAULT_ID_PRODATOG = 1;
    private static final Integer UPDATED_ID_PRODATOG = 2;

    private static final Integer DEFAULT_IZNOS = 1;
    private static final Integer UPDATED_IZNOS = 2;

    @Inject
    private DnevniPazarRepository dnevniPazarRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDnevniPazarMockMvc;

    private DnevniPazar dnevniPazar;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DnevniPazarResource dnevniPazarResource = new DnevniPazarResource();
        ReflectionTestUtils.setField(dnevniPazarResource, "dnevniPazarRepository", dnevniPazarRepository);
        this.restDnevniPazarMockMvc = MockMvcBuilders.standaloneSetup(dnevniPazarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dnevniPazar = new DnevniPazar();
        dnevniPazar.setId_prodatog(DEFAULT_ID_PRODATOG);
        dnevniPazar.setIznos(DEFAULT_IZNOS);
    }

    @Test
    @Transactional
    public void createDnevniPazar() throws Exception {
        int databaseSizeBeforeCreate = dnevniPazarRepository.findAll().size();

        // Create the DnevniPazar

        restDnevniPazarMockMvc.perform(post("/api/dnevni-pazars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dnevniPazar)))
                .andExpect(status().isCreated());

        // Validate the DnevniPazar in the database
        List<DnevniPazar> dnevniPazars = dnevniPazarRepository.findAll();
        assertThat(dnevniPazars).hasSize(databaseSizeBeforeCreate + 1);
        DnevniPazar testDnevniPazar = dnevniPazars.get(dnevniPazars.size() - 1);
        assertThat(testDnevniPazar.getId_prodatog()).isEqualTo(DEFAULT_ID_PRODATOG);
        assertThat(testDnevniPazar.getIznos()).isEqualTo(DEFAULT_IZNOS);
    }

    @Test
    @Transactional
    public void getAllDnevniPazars() throws Exception {
        // Initialize the database
        dnevniPazarRepository.saveAndFlush(dnevniPazar);

        // Get all the dnevniPazars
        restDnevniPazarMockMvc.perform(get("/api/dnevni-pazars?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dnevniPazar.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_prodatog").value(hasItem(DEFAULT_ID_PRODATOG)))
                .andExpect(jsonPath("$.[*].iznos").value(hasItem(DEFAULT_IZNOS)));
    }

    @Test
    @Transactional
    public void getDnevniPazar() throws Exception {
        // Initialize the database
        dnevniPazarRepository.saveAndFlush(dnevniPazar);

        // Get the dnevniPazar
        restDnevniPazarMockMvc.perform(get("/api/dnevni-pazars/{id}", dnevniPazar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dnevniPazar.getId().intValue()))
            .andExpect(jsonPath("$.id_prodatog").value(DEFAULT_ID_PRODATOG))
            .andExpect(jsonPath("$.iznos").value(DEFAULT_IZNOS));
    }

    @Test
    @Transactional
    public void getNonExistingDnevniPazar() throws Exception {
        // Get the dnevniPazar
        restDnevniPazarMockMvc.perform(get("/api/dnevni-pazars/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDnevniPazar() throws Exception {
        // Initialize the database
        dnevniPazarRepository.saveAndFlush(dnevniPazar);
        int databaseSizeBeforeUpdate = dnevniPazarRepository.findAll().size();

        // Update the dnevniPazar
        DnevniPazar updatedDnevniPazar = new DnevniPazar();
        updatedDnevniPazar.setId(dnevniPazar.getId());
        updatedDnevniPazar.setId_prodatog(UPDATED_ID_PRODATOG);
        updatedDnevniPazar.setIznos(UPDATED_IZNOS);

        restDnevniPazarMockMvc.perform(put("/api/dnevni-pazars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDnevniPazar)))
                .andExpect(status().isOk());

        // Validate the DnevniPazar in the database
        List<DnevniPazar> dnevniPazars = dnevniPazarRepository.findAll();
        assertThat(dnevniPazars).hasSize(databaseSizeBeforeUpdate);
        DnevniPazar testDnevniPazar = dnevniPazars.get(dnevniPazars.size() - 1);
        assertThat(testDnevniPazar.getId_prodatog()).isEqualTo(UPDATED_ID_PRODATOG);
        assertThat(testDnevniPazar.getIznos()).isEqualTo(UPDATED_IZNOS);
    }

    @Test
    @Transactional
    public void deleteDnevniPazar() throws Exception {
        // Initialize the database
        dnevniPazarRepository.saveAndFlush(dnevniPazar);
        int databaseSizeBeforeDelete = dnevniPazarRepository.findAll().size();

        // Get the dnevniPazar
        restDnevniPazarMockMvc.perform(delete("/api/dnevni-pazars/{id}", dnevniPazar.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DnevniPazar> dnevniPazars = dnevniPazarRepository.findAll();
        assertThat(dnevniPazars).hasSize(databaseSizeBeforeDelete - 1);
    }
}
