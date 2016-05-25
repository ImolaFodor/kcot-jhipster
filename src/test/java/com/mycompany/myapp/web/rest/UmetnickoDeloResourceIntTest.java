package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.UmetnickoDelo;
import com.mycompany.myapp.repository.UmetnickoDeloRepository;

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

import com.mycompany.myapp.domain.enumeration.TipUmDela;

/**
 * Test class for the UmetnickoDeloResource REST controller.
 *
 * @see UmetnickoDeloResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class UmetnickoDeloResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAA";
    private static final String UPDATED_NAZIV = "BBBBB";
    private static final String DEFAULT_UMETNIK_IME = "AAAAA";
    private static final String UPDATED_UMETNIK_IME = "BBBBB";
    private static final String DEFAULT_UMETNIK_PRZ = "AAAAA";
    private static final String UPDATED_UMETNIK_PRZ = "BBBBB";
    private static final String DEFAULT_INVENTARSKI_BROJ = "AAAAA";
    private static final String UPDATED_INVENTARSKI_BROJ = "BBBBB";

    private static final TipUmDela DEFAULT_TIP_UM_DELA = TipUmDela.MAGACIN;
    private static final TipUmDela UPDATED_TIP_UM_DELA = TipUmDela.SVOJINA;

    @Inject
    private UmetnickoDeloRepository umetnickoDeloRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUmetnickoDeloMockMvc;

    private UmetnickoDelo umetnickoDelo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UmetnickoDeloResource umetnickoDeloResource = new UmetnickoDeloResource();
        ReflectionTestUtils.setField(umetnickoDeloResource, "umetnickoDeloRepository", umetnickoDeloRepository);
        this.restUmetnickoDeloMockMvc = MockMvcBuilders.standaloneSetup(umetnickoDeloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        umetnickoDelo = new UmetnickoDelo();
        umetnickoDelo.setNaziv(DEFAULT_NAZIV);
        umetnickoDelo.setUmetnik_ime(DEFAULT_UMETNIK_IME);
        umetnickoDelo.setUmetnik_prz(DEFAULT_UMETNIK_PRZ);
        umetnickoDelo.setInventarski_broj(DEFAULT_INVENTARSKI_BROJ);
        umetnickoDelo.setTip_um_dela(DEFAULT_TIP_UM_DELA);
    }

    @Test
    @Transactional
    public void createUmetnickoDelo() throws Exception {
        int databaseSizeBeforeCreate = umetnickoDeloRepository.findAll().size();

        // Create the UmetnickoDelo

        restUmetnickoDeloMockMvc.perform(post("/api/umetnicko-delos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umetnickoDelo)))
                .andExpect(status().isCreated());

        // Validate the UmetnickoDelo in the database
        List<UmetnickoDelo> umetnickoDelos = umetnickoDeloRepository.findAll();
        assertThat(umetnickoDelos).hasSize(databaseSizeBeforeCreate + 1);
        UmetnickoDelo testUmetnickoDelo = umetnickoDelos.get(umetnickoDelos.size() - 1);
        assertThat(testUmetnickoDelo.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testUmetnickoDelo.getUmetnik_ime()).isEqualTo(DEFAULT_UMETNIK_IME);
        assertThat(testUmetnickoDelo.getUmetnik_prz()).isEqualTo(DEFAULT_UMETNIK_PRZ);
        assertThat(testUmetnickoDelo.getInventarski_broj()).isEqualTo(DEFAULT_INVENTARSKI_BROJ);
        assertThat(testUmetnickoDelo.getTip_um_dela()).isEqualTo(DEFAULT_TIP_UM_DELA);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = umetnickoDeloRepository.findAll().size();
        // set the field null
        umetnickoDelo.setNaziv(null);

        // Create the UmetnickoDelo, which fails.

        restUmetnickoDeloMockMvc.perform(post("/api/umetnicko-delos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umetnickoDelo)))
                .andExpect(status().isBadRequest());

        List<UmetnickoDelo> umetnickoDelos = umetnickoDeloRepository.findAll();
        assertThat(umetnickoDelos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTip_um_delaIsRequired() throws Exception {
        int databaseSizeBeforeTest = umetnickoDeloRepository.findAll().size();
        // set the field null
        umetnickoDelo.setTip_um_dela(null);

        // Create the UmetnickoDelo, which fails.

        restUmetnickoDeloMockMvc.perform(post("/api/umetnicko-delos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(umetnickoDelo)))
                .andExpect(status().isBadRequest());

        List<UmetnickoDelo> umetnickoDelos = umetnickoDeloRepository.findAll();
        assertThat(umetnickoDelos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmetnickoDelos() throws Exception {
        // Initialize the database
        umetnickoDeloRepository.saveAndFlush(umetnickoDelo);

        // Get all the umetnickoDelos
        restUmetnickoDeloMockMvc.perform(get("/api/umetnicko-delos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(umetnickoDelo.getId().intValue())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].umetnik_ime").value(hasItem(DEFAULT_UMETNIK_IME.toString())))
                .andExpect(jsonPath("$.[*].umetnik_prz").value(hasItem(DEFAULT_UMETNIK_PRZ.toString())))
                .andExpect(jsonPath("$.[*].inventarski_broj").value(hasItem(DEFAULT_INVENTARSKI_BROJ.toString())))
                .andExpect(jsonPath("$.[*].tip_um_dela").value(hasItem(DEFAULT_TIP_UM_DELA.toString())));
    }

    @Test
    @Transactional
    public void getUmetnickoDelo() throws Exception {
        // Initialize the database
        umetnickoDeloRepository.saveAndFlush(umetnickoDelo);

        // Get the umetnickoDelo
        restUmetnickoDeloMockMvc.perform(get("/api/umetnicko-delos/{id}", umetnickoDelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(umetnickoDelo.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.umetnik_ime").value(DEFAULT_UMETNIK_IME.toString()))
            .andExpect(jsonPath("$.umetnik_prz").value(DEFAULT_UMETNIK_PRZ.toString()))
            .andExpect(jsonPath("$.inventarski_broj").value(DEFAULT_INVENTARSKI_BROJ.toString()))
            .andExpect(jsonPath("$.tip_um_dela").value(DEFAULT_TIP_UM_DELA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUmetnickoDelo() throws Exception {
        // Get the umetnickoDelo
        restUmetnickoDeloMockMvc.perform(get("/api/umetnicko-delos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmetnickoDelo() throws Exception {
        // Initialize the database
        umetnickoDeloRepository.saveAndFlush(umetnickoDelo);
        int databaseSizeBeforeUpdate = umetnickoDeloRepository.findAll().size();

        // Update the umetnickoDelo
        UmetnickoDelo updatedUmetnickoDelo = new UmetnickoDelo();
        updatedUmetnickoDelo.setId(umetnickoDelo.getId());
        updatedUmetnickoDelo.setNaziv(UPDATED_NAZIV);
        updatedUmetnickoDelo.setUmetnik_ime(UPDATED_UMETNIK_IME);
        updatedUmetnickoDelo.setUmetnik_prz(UPDATED_UMETNIK_PRZ);
        updatedUmetnickoDelo.setInventarski_broj(UPDATED_INVENTARSKI_BROJ);
        updatedUmetnickoDelo.setTip_um_dela(UPDATED_TIP_UM_DELA);

        restUmetnickoDeloMockMvc.perform(put("/api/umetnicko-delos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUmetnickoDelo)))
                .andExpect(status().isOk());

        // Validate the UmetnickoDelo in the database
        List<UmetnickoDelo> umetnickoDelos = umetnickoDeloRepository.findAll();
        assertThat(umetnickoDelos).hasSize(databaseSizeBeforeUpdate);
        UmetnickoDelo testUmetnickoDelo = umetnickoDelos.get(umetnickoDelos.size() - 1);
        assertThat(testUmetnickoDelo.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testUmetnickoDelo.getUmetnik_ime()).isEqualTo(UPDATED_UMETNIK_IME);
        assertThat(testUmetnickoDelo.getUmetnik_prz()).isEqualTo(UPDATED_UMETNIK_PRZ);
        assertThat(testUmetnickoDelo.getInventarski_broj()).isEqualTo(UPDATED_INVENTARSKI_BROJ);
        assertThat(testUmetnickoDelo.getTip_um_dela()).isEqualTo(UPDATED_TIP_UM_DELA);
    }

    @Test
    @Transactional
    public void deleteUmetnickoDelo() throws Exception {
        // Initialize the database
        umetnickoDeloRepository.saveAndFlush(umetnickoDelo);
        int databaseSizeBeforeDelete = umetnickoDeloRepository.findAll().size();

        // Get the umetnickoDelo
        restUmetnickoDeloMockMvc.perform(delete("/api/umetnicko-delos/{id}", umetnickoDelo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UmetnickoDelo> umetnickoDelos = umetnickoDeloRepository.findAll();
        assertThat(umetnickoDelos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
