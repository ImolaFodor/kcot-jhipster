package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.USali;
import com.mycompany.myapp.repository.USaliRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.StatusGal;

/**
 * Test class for the USaliResource REST controller.
 *
 * @see USaliResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class USaliResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAZIV = "AAAAA";
    private static final String UPDATED_NAZIV = "BBBBB";

    private static final Integer DEFAULT_POSLOVNA_GODINA = 1;
    private static final Integer UPDATED_POSLOVNA_GODINA = 2;
    private static final String DEFAULT_KONTAKT_IME = "AAAAA";
    private static final String UPDATED_KONTAKT_IME = "BBBBB";
    private static final String DEFAULT_KONTAKT_PRZ = "AAAAA";
    private static final String UPDATED_KONTAKT_PRZ = "BBBBB";

    private static final Integer DEFAULT_KONTAKT_BROJ = 1;
    private static final Integer UPDATED_KONTAKT_BROJ = 2;
    private static final String DEFAULT_KONTAKT_EMAIL = "AAAAA";
    private static final String UPDATED_KONTAKT_EMAIL = "BBBBB";

    private static final Integer DEFAULT_ZARADA = 1;
    private static final Integer UPDATED_ZARADA = 2;

    private static final Integer DEFAULT_PRIHOD = 1;
    private static final Integer UPDATED_PRIHOD = 2;

    private static final Integer DEFAULT_PROCENAT = 1;
    private static final Integer UPDATED_PROCENAT = 2;

    private static final Integer DEFAULT_POSECENOST = 1;
    private static final Integer UPDATED_POSECENOST = 2;

    private static final Boolean DEFAULT_TITL = false;
    private static final Boolean UPDATED_TITL = true;

    private static final Boolean DEFAULT_OPREMA = false;
    private static final Boolean UPDATED_OPREMA = true;

    private static final Long DEFAULT_NAPOMENE = 1L;
    private static final Long UPDATED_NAPOMENE = 2L;

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATUM_STR = dateTimeFormatter.format(DEFAULT_DATUM);

    private static final StatusGal DEFAULT_STATUS = StatusGal.REALIZOVANO;
    private static final StatusGal UPDATED_STATUS = StatusGal.U_PLANU;

    @Inject
    private USaliRepository uSaliRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUSaliMockMvc;

    private USali uSali;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        USaliResource uSaliResource = new USaliResource();
        ReflectionTestUtils.setField(uSaliResource, "uSaliRepository", uSaliRepository);
        this.restUSaliMockMvc = MockMvcBuilders.standaloneSetup(uSaliResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        uSali = new USali();
        uSali.setNaziv(DEFAULT_NAZIV);
        uSali.setPoslovna_godina(DEFAULT_POSLOVNA_GODINA);
        uSali.setKontakt_ime(DEFAULT_KONTAKT_IME);
        uSali.setKontakt_prz(DEFAULT_KONTAKT_PRZ);
        uSali.setKontakt_broj(DEFAULT_KONTAKT_BROJ);
        uSali.setKontakt_email(DEFAULT_KONTAKT_EMAIL);
        uSali.setZarada(DEFAULT_ZARADA);
        uSali.setPrihod(DEFAULT_PRIHOD);
        uSali.setProcenat(DEFAULT_PROCENAT);
        uSali.setPosecenost(DEFAULT_POSECENOST);
        uSali.setTitl(DEFAULT_TITL);
        uSali.setOprema(DEFAULT_OPREMA);
        uSali.setNapomene(DEFAULT_NAPOMENE);
        uSali.setDatum(DEFAULT_DATUM);
        uSali.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUSali() throws Exception {
        int databaseSizeBeforeCreate = uSaliRepository.findAll().size();

        // Create the USali

        restUSaliMockMvc.perform(post("/api/u-salis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uSali)))
                .andExpect(status().isCreated());

        // Validate the USali in the database
        List<USali> uSalis = uSaliRepository.findAll();
        assertThat(uSalis).hasSize(databaseSizeBeforeCreate + 1);
        USali testUSali = uSalis.get(uSalis.size() - 1);
        assertThat(testUSali.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testUSali.getPoslovna_godina()).isEqualTo(DEFAULT_POSLOVNA_GODINA);
        assertThat(testUSali.getKontakt_ime()).isEqualTo(DEFAULT_KONTAKT_IME);
        assertThat(testUSali.getKontakt_prz()).isEqualTo(DEFAULT_KONTAKT_PRZ);
        assertThat(testUSali.getKontakt_broj()).isEqualTo(DEFAULT_KONTAKT_BROJ);
        assertThat(testUSali.getKontakt_email()).isEqualTo(DEFAULT_KONTAKT_EMAIL);
        assertThat(testUSali.getZarada()).isEqualTo(DEFAULT_ZARADA);
        assertThat(testUSali.getPrihod()).isEqualTo(DEFAULT_PRIHOD);
        assertThat(testUSali.getProcenat()).isEqualTo(DEFAULT_PROCENAT);
        assertThat(testUSali.getPosecenost()).isEqualTo(DEFAULT_POSECENOST);
        assertThat(testUSali.isTitl()).isEqualTo(DEFAULT_TITL);
        assertThat(testUSali.isOprema()).isEqualTo(DEFAULT_OPREMA);
        assertThat(testUSali.getNapomene()).isEqualTo(DEFAULT_NAPOMENE);
        assertThat(testUSali.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testUSali.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = uSaliRepository.findAll().size();
        // set the field null
        uSali.setNaziv(null);

        // Create the USali, which fails.

        restUSaliMockMvc.perform(post("/api/u-salis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uSali)))
                .andExpect(status().isBadRequest());

        List<USali> uSalis = uSaliRepository.findAll();
        assertThat(uSalis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoslovna_godinaIsRequired() throws Exception {
        int databaseSizeBeforeTest = uSaliRepository.findAll().size();
        // set the field null
        uSali.setPoslovna_godina(null);

        // Create the USali, which fails.

        restUSaliMockMvc.perform(post("/api/u-salis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uSali)))
                .andExpect(status().isBadRequest());

        List<USali> uSalis = uSaliRepository.findAll();
        assertThat(uSalis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUSalis() throws Exception {
        // Initialize the database
        uSaliRepository.saveAndFlush(uSali);

        // Get all the uSalis
        restUSaliMockMvc.perform(get("/api/u-salis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(uSali.getId().intValue())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].poslovna_godina").value(hasItem(DEFAULT_POSLOVNA_GODINA)))
                .andExpect(jsonPath("$.[*].kontakt_ime").value(hasItem(DEFAULT_KONTAKT_IME.toString())))
                .andExpect(jsonPath("$.[*].kontakt_prz").value(hasItem(DEFAULT_KONTAKT_PRZ.toString())))
                .andExpect(jsonPath("$.[*].kontakt_broj").value(hasItem(DEFAULT_KONTAKT_BROJ)))
                .andExpect(jsonPath("$.[*].kontakt_email").value(hasItem(DEFAULT_KONTAKT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].zarada").value(hasItem(DEFAULT_ZARADA)))
                .andExpect(jsonPath("$.[*].prihod").value(hasItem(DEFAULT_PRIHOD)))
                .andExpect(jsonPath("$.[*].procenat").value(hasItem(DEFAULT_PROCENAT)))
                .andExpect(jsonPath("$.[*].posecenost").value(hasItem(DEFAULT_POSECENOST)))
                .andExpect(jsonPath("$.[*].titl").value(hasItem(DEFAULT_TITL.booleanValue())))
                .andExpect(jsonPath("$.[*].oprema").value(hasItem(DEFAULT_OPREMA.booleanValue())))
                .andExpect(jsonPath("$.[*].napomene").value(hasItem(DEFAULT_NAPOMENE.intValue())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM_STR)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getUSali() throws Exception {
        // Initialize the database
        uSaliRepository.saveAndFlush(uSali);

        // Get the uSali
        restUSaliMockMvc.perform(get("/api/u-salis/{id}", uSali.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(uSali.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.poslovna_godina").value(DEFAULT_POSLOVNA_GODINA))
            .andExpect(jsonPath("$.kontakt_ime").value(DEFAULT_KONTAKT_IME.toString()))
            .andExpect(jsonPath("$.kontakt_prz").value(DEFAULT_KONTAKT_PRZ.toString()))
            .andExpect(jsonPath("$.kontakt_broj").value(DEFAULT_KONTAKT_BROJ))
            .andExpect(jsonPath("$.kontakt_email").value(DEFAULT_KONTAKT_EMAIL.toString()))
            .andExpect(jsonPath("$.zarada").value(DEFAULT_ZARADA))
            .andExpect(jsonPath("$.prihod").value(DEFAULT_PRIHOD))
            .andExpect(jsonPath("$.procenat").value(DEFAULT_PROCENAT))
            .andExpect(jsonPath("$.posecenost").value(DEFAULT_POSECENOST))
            .andExpect(jsonPath("$.titl").value(DEFAULT_TITL.booleanValue()))
            .andExpect(jsonPath("$.oprema").value(DEFAULT_OPREMA.booleanValue()))
            .andExpect(jsonPath("$.napomene").value(DEFAULT_NAPOMENE.intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUSali() throws Exception {
        // Get the uSali
        restUSaliMockMvc.perform(get("/api/u-salis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUSali() throws Exception {
        // Initialize the database
        uSaliRepository.saveAndFlush(uSali);
        int databaseSizeBeforeUpdate = uSaliRepository.findAll().size();

        // Update the uSali
        USali updatedUSali = new USali();
        updatedUSali.setId(uSali.getId());
        updatedUSali.setNaziv(UPDATED_NAZIV);
        updatedUSali.setPoslovna_godina(UPDATED_POSLOVNA_GODINA);
        updatedUSali.setKontakt_ime(UPDATED_KONTAKT_IME);
        updatedUSali.setKontakt_prz(UPDATED_KONTAKT_PRZ);
        updatedUSali.setKontakt_broj(UPDATED_KONTAKT_BROJ);
        updatedUSali.setKontakt_email(UPDATED_KONTAKT_EMAIL);
        updatedUSali.setZarada(UPDATED_ZARADA);
        updatedUSali.setPrihod(UPDATED_PRIHOD);
        updatedUSali.setProcenat(UPDATED_PROCENAT);
        updatedUSali.setPosecenost(UPDATED_POSECENOST);
        updatedUSali.setTitl(UPDATED_TITL);
        updatedUSali.setOprema(UPDATED_OPREMA);
        updatedUSali.setNapomene(UPDATED_NAPOMENE);
        updatedUSali.setDatum(UPDATED_DATUM);
        updatedUSali.setStatus(UPDATED_STATUS);

        restUSaliMockMvc.perform(put("/api/u-salis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUSali)))
                .andExpect(status().isOk());

        // Validate the USali in the database
        List<USali> uSalis = uSaliRepository.findAll();
        assertThat(uSalis).hasSize(databaseSizeBeforeUpdate);
        USali testUSali = uSalis.get(uSalis.size() - 1);
        assertThat(testUSali.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testUSali.getPoslovna_godina()).isEqualTo(UPDATED_POSLOVNA_GODINA);
        assertThat(testUSali.getKontakt_ime()).isEqualTo(UPDATED_KONTAKT_IME);
        assertThat(testUSali.getKontakt_prz()).isEqualTo(UPDATED_KONTAKT_PRZ);
        assertThat(testUSali.getKontakt_broj()).isEqualTo(UPDATED_KONTAKT_BROJ);
        assertThat(testUSali.getKontakt_email()).isEqualTo(UPDATED_KONTAKT_EMAIL);
        assertThat(testUSali.getZarada()).isEqualTo(UPDATED_ZARADA);
        assertThat(testUSali.getPrihod()).isEqualTo(UPDATED_PRIHOD);
        assertThat(testUSali.getProcenat()).isEqualTo(UPDATED_PROCENAT);
        assertThat(testUSali.getPosecenost()).isEqualTo(UPDATED_POSECENOST);
        assertThat(testUSali.isTitl()).isEqualTo(UPDATED_TITL);
        assertThat(testUSali.isOprema()).isEqualTo(UPDATED_OPREMA);
        assertThat(testUSali.getNapomene()).isEqualTo(UPDATED_NAPOMENE);
        assertThat(testUSali.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testUSali.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteUSali() throws Exception {
        // Initialize the database
        uSaliRepository.saveAndFlush(uSali);
        int databaseSizeBeforeDelete = uSaliRepository.findAll().size();

        // Get the uSali
        restUSaliMockMvc.perform(delete("/api/u-salis/{id}", uSali.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<USali> uSalis = uSaliRepository.findAll();
        assertThat(uSalis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
