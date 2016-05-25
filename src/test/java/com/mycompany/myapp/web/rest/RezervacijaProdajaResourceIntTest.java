package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.RezervacijaProdaja;
import com.mycompany.myapp.repository.RezervacijaProdajaRepository;

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

import com.mycompany.myapp.domain.enumeration.StatusRezProd;

/**
 * Test class for the RezervacijaProdajaResource REST controller.
 *
 * @see RezervacijaProdajaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class RezervacijaProdajaResourceIntTest {

    private static final String DEFAULT_REZERVISAO_IME = "AAAAA";
    private static final String UPDATED_REZERVISAO_IME = "BBBBB";

    private static final Integer DEFAULT_CENA = 1;
    private static final Integer UPDATED_CENA = 2;

    private static final Integer DEFAULT_BROJ_KARATA = 1;
    private static final Integer UPDATED_BROJ_KARATA = 2;

    private static final Integer DEFAULT_BR_MALE_DECE = 1;
    private static final Integer UPDATED_BR_MALE_DECE = 2;

    private static final Integer DEFAULT_BR_VELIKE_DECE = 1;
    private static final Integer UPDATED_BR_VELIKE_DECE = 2;
    private static final String DEFAULT_FIRMA = "AAAAA";
    private static final String UPDATED_FIRMA = "BBBBB";

    private static final Boolean DEFAULT_AKTIVNA_REZ = false;
    private static final Boolean UPDATED_AKTIVNA_REZ = true;

    private static final Long DEFAULT_OPIS = 1L;
    private static final Long UPDATED_OPIS = 2L;

    private static final StatusRezProd DEFAULT_STATUS_REZ_PROD = StatusRezProd.REZERVISANO;
    private static final StatusRezProd UPDATED_STATUS_REZ_PROD = StatusRezProd.PRODATO;

    @Inject
    private RezervacijaProdajaRepository rezervacijaProdajaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRezervacijaProdajaMockMvc;

    private RezervacijaProdaja rezervacijaProdaja;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RezervacijaProdajaResource rezervacijaProdajaResource = new RezervacijaProdajaResource();
        ReflectionTestUtils.setField(rezervacijaProdajaResource, "rezervacijaProdajaRepository", rezervacijaProdajaRepository);
        this.restRezervacijaProdajaMockMvc = MockMvcBuilders.standaloneSetup(rezervacijaProdajaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rezervacijaProdaja = new RezervacijaProdaja();
        rezervacijaProdaja.setRezervisao_ime(DEFAULT_REZERVISAO_IME);
        rezervacijaProdaja.setCena(DEFAULT_CENA);
        rezervacijaProdaja.setBroj_karata(DEFAULT_BROJ_KARATA);
        rezervacijaProdaja.setBr_male_dece(DEFAULT_BR_MALE_DECE);
        rezervacijaProdaja.setBr_velike_dece(DEFAULT_BR_VELIKE_DECE);
        rezervacijaProdaja.setFirma(DEFAULT_FIRMA);
        rezervacijaProdaja.setAktivna_rez(DEFAULT_AKTIVNA_REZ);
        rezervacijaProdaja.setOpis(DEFAULT_OPIS);
        rezervacijaProdaja.setStatus_rez_prod(DEFAULT_STATUS_REZ_PROD);
    }

    @Test
    @Transactional
    public void createRezervacijaProdaja() throws Exception {
        int databaseSizeBeforeCreate = rezervacijaProdajaRepository.findAll().size();

        // Create the RezervacijaProdaja

        restRezervacijaProdajaMockMvc.perform(post("/api/rezervacija-prodajas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rezervacijaProdaja)))
                .andExpect(status().isCreated());

        // Validate the RezervacijaProdaja in the database
        List<RezervacijaProdaja> rezervacijaProdajas = rezervacijaProdajaRepository.findAll();
        assertThat(rezervacijaProdajas).hasSize(databaseSizeBeforeCreate + 1);
        RezervacijaProdaja testRezervacijaProdaja = rezervacijaProdajas.get(rezervacijaProdajas.size() - 1);
        assertThat(testRezervacijaProdaja.getRezervisao_ime()).isEqualTo(DEFAULT_REZERVISAO_IME);
        assertThat(testRezervacijaProdaja.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testRezervacijaProdaja.getBroj_karata()).isEqualTo(DEFAULT_BROJ_KARATA);
        assertThat(testRezervacijaProdaja.getBr_male_dece()).isEqualTo(DEFAULT_BR_MALE_DECE);
        assertThat(testRezervacijaProdaja.getBr_velike_dece()).isEqualTo(DEFAULT_BR_VELIKE_DECE);
        assertThat(testRezervacijaProdaja.getFirma()).isEqualTo(DEFAULT_FIRMA);
        assertThat(testRezervacijaProdaja.isAktivna_rez()).isEqualTo(DEFAULT_AKTIVNA_REZ);
        assertThat(testRezervacijaProdaja.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testRezervacijaProdaja.getStatus_rez_prod()).isEqualTo(DEFAULT_STATUS_REZ_PROD);
    }

    @Test
    @Transactional
    public void checkStatus_rez_prodIsRequired() throws Exception {
        int databaseSizeBeforeTest = rezervacijaProdajaRepository.findAll().size();
        // set the field null
        rezervacijaProdaja.setStatus_rez_prod(null);

        // Create the RezervacijaProdaja, which fails.

        restRezervacijaProdajaMockMvc.perform(post("/api/rezervacija-prodajas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rezervacijaProdaja)))
                .andExpect(status().isBadRequest());

        List<RezervacijaProdaja> rezervacijaProdajas = rezervacijaProdajaRepository.findAll();
        assertThat(rezervacijaProdajas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRezervacijaProdajas() throws Exception {
        // Initialize the database
        rezervacijaProdajaRepository.saveAndFlush(rezervacijaProdaja);

        // Get all the rezervacijaProdajas
        restRezervacijaProdajaMockMvc.perform(get("/api/rezervacija-prodajas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rezervacijaProdaja.getId().intValue())))
                .andExpect(jsonPath("$.[*].rezervisao_ime").value(hasItem(DEFAULT_REZERVISAO_IME.toString())))
                .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA)))
                .andExpect(jsonPath("$.[*].broj_karata").value(hasItem(DEFAULT_BROJ_KARATA)))
                .andExpect(jsonPath("$.[*].br_male_dece").value(hasItem(DEFAULT_BR_MALE_DECE)))
                .andExpect(jsonPath("$.[*].br_velike_dece").value(hasItem(DEFAULT_BR_VELIKE_DECE)))
                .andExpect(jsonPath("$.[*].firma").value(hasItem(DEFAULT_FIRMA.toString())))
                .andExpect(jsonPath("$.[*].aktivna_rez").value(hasItem(DEFAULT_AKTIVNA_REZ.booleanValue())))
                .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.intValue())))
                .andExpect(jsonPath("$.[*].status_rez_prod").value(hasItem(DEFAULT_STATUS_REZ_PROD.toString())));
    }

    @Test
    @Transactional
    public void getRezervacijaProdaja() throws Exception {
        // Initialize the database
        rezervacijaProdajaRepository.saveAndFlush(rezervacijaProdaja);

        // Get the rezervacijaProdaja
        restRezervacijaProdajaMockMvc.perform(get("/api/rezervacija-prodajas/{id}", rezervacijaProdaja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rezervacijaProdaja.getId().intValue()))
            .andExpect(jsonPath("$.rezervisao_ime").value(DEFAULT_REZERVISAO_IME.toString()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA))
            .andExpect(jsonPath("$.broj_karata").value(DEFAULT_BROJ_KARATA))
            .andExpect(jsonPath("$.br_male_dece").value(DEFAULT_BR_MALE_DECE))
            .andExpect(jsonPath("$.br_velike_dece").value(DEFAULT_BR_VELIKE_DECE))
            .andExpect(jsonPath("$.firma").value(DEFAULT_FIRMA.toString()))
            .andExpect(jsonPath("$.aktivna_rez").value(DEFAULT_AKTIVNA_REZ.booleanValue()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.intValue()))
            .andExpect(jsonPath("$.status_rez_prod").value(DEFAULT_STATUS_REZ_PROD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRezervacijaProdaja() throws Exception {
        // Get the rezervacijaProdaja
        restRezervacijaProdajaMockMvc.perform(get("/api/rezervacija-prodajas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRezervacijaProdaja() throws Exception {
        // Initialize the database
        rezervacijaProdajaRepository.saveAndFlush(rezervacijaProdaja);
        int databaseSizeBeforeUpdate = rezervacijaProdajaRepository.findAll().size();

        // Update the rezervacijaProdaja
        RezervacijaProdaja updatedRezervacijaProdaja = new RezervacijaProdaja();
        updatedRezervacijaProdaja.setId(rezervacijaProdaja.getId());
        updatedRezervacijaProdaja.setRezervisao_ime(UPDATED_REZERVISAO_IME);
        updatedRezervacijaProdaja.setCena(UPDATED_CENA);
        updatedRezervacijaProdaja.setBroj_karata(UPDATED_BROJ_KARATA);
        updatedRezervacijaProdaja.setBr_male_dece(UPDATED_BR_MALE_DECE);
        updatedRezervacijaProdaja.setBr_velike_dece(UPDATED_BR_VELIKE_DECE);
        updatedRezervacijaProdaja.setFirma(UPDATED_FIRMA);
        updatedRezervacijaProdaja.setAktivna_rez(UPDATED_AKTIVNA_REZ);
        updatedRezervacijaProdaja.setOpis(UPDATED_OPIS);
        updatedRezervacijaProdaja.setStatus_rez_prod(UPDATED_STATUS_REZ_PROD);

        restRezervacijaProdajaMockMvc.perform(put("/api/rezervacija-prodajas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRezervacijaProdaja)))
                .andExpect(status().isOk());

        // Validate the RezervacijaProdaja in the database
        List<RezervacijaProdaja> rezervacijaProdajas = rezervacijaProdajaRepository.findAll();
        assertThat(rezervacijaProdajas).hasSize(databaseSizeBeforeUpdate);
        RezervacijaProdaja testRezervacijaProdaja = rezervacijaProdajas.get(rezervacijaProdajas.size() - 1);
        assertThat(testRezervacijaProdaja.getRezervisao_ime()).isEqualTo(UPDATED_REZERVISAO_IME);
        assertThat(testRezervacijaProdaja.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testRezervacijaProdaja.getBroj_karata()).isEqualTo(UPDATED_BROJ_KARATA);
        assertThat(testRezervacijaProdaja.getBr_male_dece()).isEqualTo(UPDATED_BR_MALE_DECE);
        assertThat(testRezervacijaProdaja.getBr_velike_dece()).isEqualTo(UPDATED_BR_VELIKE_DECE);
        assertThat(testRezervacijaProdaja.getFirma()).isEqualTo(UPDATED_FIRMA);
        assertThat(testRezervacijaProdaja.isAktivna_rez()).isEqualTo(UPDATED_AKTIVNA_REZ);
        assertThat(testRezervacijaProdaja.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testRezervacijaProdaja.getStatus_rez_prod()).isEqualTo(UPDATED_STATUS_REZ_PROD);
    }

    @Test
    @Transactional
    public void deleteRezervacijaProdaja() throws Exception {
        // Initialize the database
        rezervacijaProdajaRepository.saveAndFlush(rezervacijaProdaja);
        int databaseSizeBeforeDelete = rezervacijaProdajaRepository.findAll().size();

        // Get the rezervacijaProdaja
        restRezervacijaProdajaMockMvc.perform(delete("/api/rezervacija-prodajas/{id}", rezervacijaProdaja.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RezervacijaProdaja> rezervacijaProdajas = rezervacijaProdajaRepository.findAll();
        assertThat(rezervacijaProdajas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
