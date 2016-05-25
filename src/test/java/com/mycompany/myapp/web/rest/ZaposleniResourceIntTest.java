package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.Zaposleni;
import com.mycompany.myapp.repository.ZaposleniRepository;

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

import com.mycompany.myapp.domain.enumeration.Tip;

/**
 * Test class for the ZaposleniResource REST controller.
 *
 * @see ZaposleniResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class ZaposleniResourceIntTest {

    private static final String DEFAULT_IME = "AAAAA";
    private static final String UPDATED_IME = "BBBBB";
    private static final String DEFAULT_PRZ = "AAAAA";
    private static final String UPDATED_PRZ = "BBBBB";
    private static final String DEFAULT_KORISNICKO_IME = "AAAAA";
    private static final String UPDATED_KORISNICKO_IME = "BBBBB";
    private static final String DEFAULT_LOZINKA = "AAAAA";
    private static final String UPDATED_LOZINKA = "BBBBB";

    private static final Tip DEFAULT_TIP = Tip.ORGANIZATOR;
    private static final Tip UPDATED_TIP = Tip.ZAPOSLEN;

    @Inject
    private ZaposleniRepository zaposleniRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restZaposleniMockMvc;

    private Zaposleni zaposleni;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ZaposleniResource zaposleniResource = new ZaposleniResource();
        ReflectionTestUtils.setField(zaposleniResource, "zaposleniRepository", zaposleniRepository);
        this.restZaposleniMockMvc = MockMvcBuilders.standaloneSetup(zaposleniResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        zaposleni = new Zaposleni();
        zaposleni.setIme(DEFAULT_IME);
        zaposleni.setPrz(DEFAULT_PRZ);
        zaposleni.setKorisnicko_ime(DEFAULT_KORISNICKO_IME);
        zaposleni.setLozinka(DEFAULT_LOZINKA);
        zaposleni.setTip(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createZaposleni() throws Exception {
        int databaseSizeBeforeCreate = zaposleniRepository.findAll().size();

        // Create the Zaposleni

        restZaposleniMockMvc.perform(post("/api/zaposlenis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
                .andExpect(status().isCreated());

        // Validate the Zaposleni in the database
        List<Zaposleni> zaposlenis = zaposleniRepository.findAll();
        assertThat(zaposlenis).hasSize(databaseSizeBeforeCreate + 1);
        Zaposleni testZaposleni = zaposlenis.get(zaposlenis.size() - 1);
        assertThat(testZaposleni.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testZaposleni.getPrz()).isEqualTo(DEFAULT_PRZ);
        assertThat(testZaposleni.getKorisnicko_ime()).isEqualTo(DEFAULT_KORISNICKO_IME);
        assertThat(testZaposleni.getLozinka()).isEqualTo(DEFAULT_LOZINKA);
        assertThat(testZaposleni.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void checkImeIsRequired() throws Exception {
        int databaseSizeBeforeTest = zaposleniRepository.findAll().size();
        // set the field null
        zaposleni.setIme(null);

        // Create the Zaposleni, which fails.

        restZaposleniMockMvc.perform(post("/api/zaposlenis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
                .andExpect(status().isBadRequest());

        List<Zaposleni> zaposlenis = zaposleniRepository.findAll();
        assertThat(zaposlenis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrzIsRequired() throws Exception {
        int databaseSizeBeforeTest = zaposleniRepository.findAll().size();
        // set the field null
        zaposleni.setPrz(null);

        // Create the Zaposleni, which fails.

        restZaposleniMockMvc.perform(post("/api/zaposlenis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
                .andExpect(status().isBadRequest());

        List<Zaposleni> zaposlenis = zaposleniRepository.findAll();
        assertThat(zaposlenis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = zaposleniRepository.findAll().size();
        // set the field null
        zaposleni.setTip(null);

        // Create the Zaposleni, which fails.

        restZaposleniMockMvc.perform(post("/api/zaposlenis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
                .andExpect(status().isBadRequest());

        List<Zaposleni> zaposlenis = zaposleniRepository.findAll();
        assertThat(zaposlenis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZaposlenis() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);

        // Get all the zaposlenis
        restZaposleniMockMvc.perform(get("/api/zaposlenis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(zaposleni.getId().intValue())))
                .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
                .andExpect(jsonPath("$.[*].prz").value(hasItem(DEFAULT_PRZ.toString())))
                .andExpect(jsonPath("$.[*].korisnicko_ime").value(hasItem(DEFAULT_KORISNICKO_IME.toString())))
                .andExpect(jsonPath("$.[*].lozinka").value(hasItem(DEFAULT_LOZINKA.toString())))
                .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())));
    }

    @Test
    @Transactional
    public void getZaposleni() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);

        // Get the zaposleni
        restZaposleniMockMvc.perform(get("/api/zaposlenis/{id}", zaposleni.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(zaposleni.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prz").value(DEFAULT_PRZ.toString()))
            .andExpect(jsonPath("$.korisnicko_ime").value(DEFAULT_KORISNICKO_IME.toString()))
            .andExpect(jsonPath("$.lozinka").value(DEFAULT_LOZINKA.toString()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZaposleni() throws Exception {
        // Get the zaposleni
        restZaposleniMockMvc.perform(get("/api/zaposlenis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZaposleni() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);
        int databaseSizeBeforeUpdate = zaposleniRepository.findAll().size();

        // Update the zaposleni
        Zaposleni updatedZaposleni = new Zaposleni();
        updatedZaposleni.setId(zaposleni.getId());
        updatedZaposleni.setIme(UPDATED_IME);
        updatedZaposleni.setPrz(UPDATED_PRZ);
        updatedZaposleni.setKorisnicko_ime(UPDATED_KORISNICKO_IME);
        updatedZaposleni.setLozinka(UPDATED_LOZINKA);
        updatedZaposleni.setTip(UPDATED_TIP);

        restZaposleniMockMvc.perform(put("/api/zaposlenis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedZaposleni)))
                .andExpect(status().isOk());

        // Validate the Zaposleni in the database
        List<Zaposleni> zaposlenis = zaposleniRepository.findAll();
        assertThat(zaposlenis).hasSize(databaseSizeBeforeUpdate);
        Zaposleni testZaposleni = zaposlenis.get(zaposlenis.size() - 1);
        assertThat(testZaposleni.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testZaposleni.getPrz()).isEqualTo(UPDATED_PRZ);
        assertThat(testZaposleni.getKorisnicko_ime()).isEqualTo(UPDATED_KORISNICKO_IME);
        assertThat(testZaposleni.getLozinka()).isEqualTo(UPDATED_LOZINKA);
        assertThat(testZaposleni.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void deleteZaposleni() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);
        int databaseSizeBeforeDelete = zaposleniRepository.findAll().size();

        // Get the zaposleni
        restZaposleniMockMvc.perform(delete("/api/zaposlenis/{id}", zaposleni.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Zaposleni> zaposlenis = zaposleniRepository.findAll();
        assertThat(zaposlenis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
