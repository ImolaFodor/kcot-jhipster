package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Gost;
import com.mycompany.myapp.repository.GostRepository;

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

import com.mycompany.myapp.domain.enumeration.TipGost;

/**
 * Test class for the GostResource REST controller.
 *
 * @see GostResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class GostResourceIntTest {

    private static final String DEFAULT_IME = "AAAAA";
    private static final String UPDATED_IME = "BBBBB";
    private static final String DEFAULT_PRZ = "AAAAA";
    private static final String UPDATED_PRZ = "BBBBB";

    private static final Integer DEFAULT_BROJ = 1;
    private static final Integer UPDATED_BROJ = 2;
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ADRESA = "AAAAA";
    private static final String UPDATED_ADRESA = "BBBBB";

    private static final TipGost DEFAULT_TIP_GOST = TipGost.VIP;
    private static final TipGost UPDATED_TIP_GOST = TipGost.GRADJANIN;

    @Inject
    private GostRepository gostRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGostMockMvc;

    private Gost gost;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GostResource gostResource = new GostResource();
        ReflectionTestUtils.setField(gostResource, "gostRepository", gostRepository);
        this.restGostMockMvc = MockMvcBuilders.standaloneSetup(gostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gost = new Gost();
        gost.setIme(DEFAULT_IME);
        gost.setPrz(DEFAULT_PRZ);
        gost.setBroj(DEFAULT_BROJ);
        gost.setEmail(DEFAULT_EMAIL);
        gost.setAdresa(DEFAULT_ADRESA);
        gost.setTip_gost(DEFAULT_TIP_GOST);
    }

    @Test
    @Transactional
    public void createGost() throws Exception {
        int databaseSizeBeforeCreate = gostRepository.findAll().size();

        // Create the Gost

        restGostMockMvc.perform(post("/api/gosts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gost)))
                .andExpect(status().isCreated());

        // Validate the Gost in the database
        List<Gost> gosts = gostRepository.findAll();
        assertThat(gosts).hasSize(databaseSizeBeforeCreate + 1);
        Gost testGost = gosts.get(gosts.size() - 1);
        assertThat(testGost.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testGost.getPrz()).isEqualTo(DEFAULT_PRZ);
        assertThat(testGost.getBroj()).isEqualTo(DEFAULT_BROJ);
        assertThat(testGost.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGost.getAdresa()).isEqualTo(DEFAULT_ADRESA);
        assertThat(testGost.getTip_gost()).isEqualTo(DEFAULT_TIP_GOST);
    }

    @Test
    @Transactional
    public void checkImeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gostRepository.findAll().size();
        // set the field null
        gost.setIme(null);

        // Create the Gost, which fails.

        restGostMockMvc.perform(post("/api/gosts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gost)))
                .andExpect(status().isBadRequest());

        List<Gost> gosts = gostRepository.findAll();
        assertThat(gosts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrzIsRequired() throws Exception {
        int databaseSizeBeforeTest = gostRepository.findAll().size();
        // set the field null
        gost.setPrz(null);

        // Create the Gost, which fails.

        restGostMockMvc.perform(post("/api/gosts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gost)))
                .andExpect(status().isBadRequest());

        List<Gost> gosts = gostRepository.findAll();
        assertThat(gosts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTip_gostIsRequired() throws Exception {
        int databaseSizeBeforeTest = gostRepository.findAll().size();
        // set the field null
        gost.setTip_gost(null);

        // Create the Gost, which fails.

        restGostMockMvc.perform(post("/api/gosts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gost)))
                .andExpect(status().isBadRequest());

        List<Gost> gosts = gostRepository.findAll();
        assertThat(gosts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGosts() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);

        // Get all the gosts
        restGostMockMvc.perform(get("/api/gosts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gost.getId().intValue())))
                .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
                .andExpect(jsonPath("$.[*].prz").value(hasItem(DEFAULT_PRZ.toString())))
                .andExpect(jsonPath("$.[*].broj").value(hasItem(DEFAULT_BROJ)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].adresa").value(hasItem(DEFAULT_ADRESA.toString())))
                .andExpect(jsonPath("$.[*].tip_gost").value(hasItem(DEFAULT_TIP_GOST.toString())));
    }

    @Test
    @Transactional
    public void getGost() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);

        // Get the gost
        restGostMockMvc.perform(get("/api/gosts/{id}", gost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gost.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prz").value(DEFAULT_PRZ.toString()))
            .andExpect(jsonPath("$.broj").value(DEFAULT_BROJ))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.adresa").value(DEFAULT_ADRESA.toString()))
            .andExpect(jsonPath("$.tip_gost").value(DEFAULT_TIP_GOST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGost() throws Exception {
        // Get the gost
        restGostMockMvc.perform(get("/api/gosts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGost() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);
        int databaseSizeBeforeUpdate = gostRepository.findAll().size();

        // Update the gost
        Gost updatedGost = new Gost();
        updatedGost.setId(gost.getId());
        updatedGost.setIme(UPDATED_IME);
        updatedGost.setPrz(UPDATED_PRZ);
        updatedGost.setBroj(UPDATED_BROJ);
        updatedGost.setEmail(UPDATED_EMAIL);
        updatedGost.setAdresa(UPDATED_ADRESA);
        updatedGost.setTip_gost(UPDATED_TIP_GOST);

        restGostMockMvc.perform(put("/api/gosts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGost)))
                .andExpect(status().isOk());

        // Validate the Gost in the database
        List<Gost> gosts = gostRepository.findAll();
        assertThat(gosts).hasSize(databaseSizeBeforeUpdate);
        Gost testGost = gosts.get(gosts.size() - 1);
        assertThat(testGost.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testGost.getPrz()).isEqualTo(UPDATED_PRZ);
        assertThat(testGost.getBroj()).isEqualTo(UPDATED_BROJ);
        assertThat(testGost.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGost.getAdresa()).isEqualTo(UPDATED_ADRESA);
        assertThat(testGost.getTip_gost()).isEqualTo(UPDATED_TIP_GOST);
    }

    @Test
    @Transactional
    public void deleteGost() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);
        int databaseSizeBeforeDelete = gostRepository.findAll().size();

        // Get the gost
        restGostMockMvc.perform(delete("/api/gosts/{id}", gost.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Gost> gosts = gostRepository.findAll();
        assertThat(gosts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
