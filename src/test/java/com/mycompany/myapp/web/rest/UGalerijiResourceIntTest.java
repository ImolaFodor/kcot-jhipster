package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.KcotApp;
import com.mycompany.myapp.domain.UGaleriji;
import com.mycompany.myapp.repository.UGalerijiRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Status;
import com.mycompany.myapp.domain.enumeration.Tip;

/**
 * Test class for the UGalerijiResource REST controller.
 *
 * @see UGalerijiResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KcotApp.class)
@WebAppConfiguration
@IntegrationTest
public class UGalerijiResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAA";
    private static final String UPDATED_NAZIV = "BBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

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

    private static final Status DEFAULT_STATUS = Status.REALIZOVANO;
    private static final Status UPDATED_STATUS = Status.U_PLANU;
    private static final String DEFAULT_MODERATOR_IME = "AAAAA";
    private static final String UPDATED_MODERATOR_IME = "BBBBB";
    private static final String DEFAULT_MODERATOR_PRZ = "AAAAA";
    private static final String UPDATED_MODERATOR_PRZ = "BBBBB";
    private static final String DEFAULT_MODERATOR_BROJ = "AAAAA";
    private static final String UPDATED_MODERATOR_BROJ = "BBBBB";

    private static final Integer DEFAULT_TROSAK = 1;
    private static final Integer UPDATED_TROSAK = 2;
    private static final String DEFAULT_UNAJMIO_IME = "AAAAA";
    private static final String UPDATED_UNAJMIO_IME = "BBBBB";
    private static final String DEFAULT_UNAJMIO_PRZ = "AAAAA";
    private static final String UPDATED_UNAJMIO_PRZ = "BBBBB";
    private static final String DEFAULT_UNAJMIO_EMAIL = "AAAAA";
    private static final String UPDATED_UNAJMIO_EMAIL = "BBBBB";
    private static final String DEFAULT_BR_FAKTURE = "AAAAA";
    private static final String UPDATED_BR_FAKTURE = "BBBBB";

    private static final Integer DEFAULT_ZARADA = 1;
    private static final Integer UPDATED_ZARADA = 2;

    private static final Integer DEFAULT_POSECENOST = 1;
    private static final Integer UPDATED_POSECENOST = 2;

    private static final Tip DEFAULT_TIP = Tip.IZLOZBA;
    private static final Tip UPDATED_TIP = Tip.KNJIZEVNO_PROMOCIJA;

    private static final Long DEFAULT_NAPOMENE = 1L;
    private static final Long UPDATED_NAPOMENE = 2L;

    @Inject
    private UGalerijiRepository uGalerijiRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUGalerijiMockMvc;

    private UGaleriji uGaleriji;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UGalerijiResource uGalerijiResource = new UGalerijiResource();
        ReflectionTestUtils.setField(uGalerijiResource, "uGalerijiRepository", uGalerijiRepository);
        this.restUGalerijiMockMvc = MockMvcBuilders.standaloneSetup(uGalerijiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        uGaleriji = new UGaleriji();
        uGaleriji.setNaziv(DEFAULT_NAZIV);
        uGaleriji.setDatum(DEFAULT_DATUM);
        uGaleriji.setPoslovna_godina(DEFAULT_POSLOVNA_GODINA);
        uGaleriji.setKontakt_ime(DEFAULT_KONTAKT_IME);
        uGaleriji.setKontakt_prz(DEFAULT_KONTAKT_PRZ);
        uGaleriji.setKontakt_broj(DEFAULT_KONTAKT_BROJ);
        uGaleriji.setKontakt_email(DEFAULT_KONTAKT_EMAIL);
        uGaleriji.setStatus(DEFAULT_STATUS);
        uGaleriji.setModerator_ime(DEFAULT_MODERATOR_IME);
        uGaleriji.setModerator_prz(DEFAULT_MODERATOR_PRZ);
        uGaleriji.setModerator_broj(DEFAULT_MODERATOR_BROJ);
        uGaleriji.setTrosak(DEFAULT_TROSAK);
        uGaleriji.setUnajmio_ime(DEFAULT_UNAJMIO_IME);
        uGaleriji.setUnajmio_prz(DEFAULT_UNAJMIO_PRZ);
        uGaleriji.setUnajmio_email(DEFAULT_UNAJMIO_EMAIL);
        uGaleriji.setBr_fakture(DEFAULT_BR_FAKTURE);
        uGaleriji.setZarada(DEFAULT_ZARADA);
        uGaleriji.setPosecenost(DEFAULT_POSECENOST);
        uGaleriji.setTip(DEFAULT_TIP);
        uGaleriji.setNapomene(DEFAULT_NAPOMENE);
    }

    @Test
    @Transactional
    public void createUGaleriji() throws Exception {
        int databaseSizeBeforeCreate = uGalerijiRepository.findAll().size();

        // Create the UGaleriji

        restUGalerijiMockMvc.perform(post("/api/u-galerijis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uGaleriji)))
                .andExpect(status().isCreated());

        // Validate the UGaleriji in the database
        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeCreate + 1);
        UGaleriji testUGaleriji = uGalerijis.get(uGalerijis.size() - 1);
        assertThat(testUGaleriji.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testUGaleriji.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testUGaleriji.getPoslovna_godina()).isEqualTo(DEFAULT_POSLOVNA_GODINA);
        assertThat(testUGaleriji.getKontakt_ime()).isEqualTo(DEFAULT_KONTAKT_IME);
        assertThat(testUGaleriji.getKontakt_prz()).isEqualTo(DEFAULT_KONTAKT_PRZ);
        assertThat(testUGaleriji.getKontakt_broj()).isEqualTo(DEFAULT_KONTAKT_BROJ);
        assertThat(testUGaleriji.getKontakt_email()).isEqualTo(DEFAULT_KONTAKT_EMAIL);
        assertThat(testUGaleriji.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUGaleriji.getModerator_ime()).isEqualTo(DEFAULT_MODERATOR_IME);
        assertThat(testUGaleriji.getModerator_prz()).isEqualTo(DEFAULT_MODERATOR_PRZ);
        assertThat(testUGaleriji.getModerator_broj()).isEqualTo(DEFAULT_MODERATOR_BROJ);
        assertThat(testUGaleriji.getTrosak()).isEqualTo(DEFAULT_TROSAK);
        assertThat(testUGaleriji.getUnajmio_ime()).isEqualTo(DEFAULT_UNAJMIO_IME);
        assertThat(testUGaleriji.getUnajmio_prz()).isEqualTo(DEFAULT_UNAJMIO_PRZ);
        assertThat(testUGaleriji.getUnajmio_email()).isEqualTo(DEFAULT_UNAJMIO_EMAIL);
        assertThat(testUGaleriji.getBr_fakture()).isEqualTo(DEFAULT_BR_FAKTURE);
        assertThat(testUGaleriji.getZarada()).isEqualTo(DEFAULT_ZARADA);
        assertThat(testUGaleriji.getPosecenost()).isEqualTo(DEFAULT_POSECENOST);
        assertThat(testUGaleriji.getTip()).isEqualTo(DEFAULT_TIP);
        assertThat(testUGaleriji.getNapomene()).isEqualTo(DEFAULT_NAPOMENE);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = uGalerijiRepository.findAll().size();
        // set the field null
        uGaleriji.setNaziv(null);

        // Create the UGaleriji, which fails.

        restUGalerijiMockMvc.perform(post("/api/u-galerijis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uGaleriji)))
                .andExpect(status().isBadRequest());

        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPoslovna_godinaIsRequired() throws Exception {
        int databaseSizeBeforeTest = uGalerijiRepository.findAll().size();
        // set the field null
        uGaleriji.setPoslovna_godina(null);

        // Create the UGaleriji, which fails.

        restUGalerijiMockMvc.perform(post("/api/u-galerijis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uGaleriji)))
                .andExpect(status().isBadRequest());

        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = uGalerijiRepository.findAll().size();
        // set the field null
        uGaleriji.setStatus(null);

        // Create the UGaleriji, which fails.

        restUGalerijiMockMvc.perform(post("/api/u-galerijis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uGaleriji)))
                .andExpect(status().isBadRequest());

        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = uGalerijiRepository.findAll().size();
        // set the field null
        uGaleriji.setTip(null);

        // Create the UGaleriji, which fails.

        restUGalerijiMockMvc.perform(post("/api/u-galerijis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uGaleriji)))
                .andExpect(status().isBadRequest());

        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUGalerijis() throws Exception {
        // Initialize the database
        uGalerijiRepository.saveAndFlush(uGaleriji);

        // Get all the uGalerijis
        restUGalerijiMockMvc.perform(get("/api/u-galerijis?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(uGaleriji.getId().intValue())))
                .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
                .andExpect(jsonPath("$.[*].poslovna_godina").value(hasItem(DEFAULT_POSLOVNA_GODINA)))
                .andExpect(jsonPath("$.[*].kontakt_ime").value(hasItem(DEFAULT_KONTAKT_IME.toString())))
                .andExpect(jsonPath("$.[*].kontakt_prz").value(hasItem(DEFAULT_KONTAKT_PRZ.toString())))
                .andExpect(jsonPath("$.[*].kontakt_broj").value(hasItem(DEFAULT_KONTAKT_BROJ)))
                .andExpect(jsonPath("$.[*].kontakt_email").value(hasItem(DEFAULT_KONTAKT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].moderator_ime").value(hasItem(DEFAULT_MODERATOR_IME.toString())))
                .andExpect(jsonPath("$.[*].moderator_prz").value(hasItem(DEFAULT_MODERATOR_PRZ.toString())))
                .andExpect(jsonPath("$.[*].moderator_broj").value(hasItem(DEFAULT_MODERATOR_BROJ.toString())))
                .andExpect(jsonPath("$.[*].trosak").value(hasItem(DEFAULT_TROSAK)))
                .andExpect(jsonPath("$.[*].unajmio_ime").value(hasItem(DEFAULT_UNAJMIO_IME.toString())))
                .andExpect(jsonPath("$.[*].unajmio_prz").value(hasItem(DEFAULT_UNAJMIO_PRZ.toString())))
                .andExpect(jsonPath("$.[*].unajmio_email").value(hasItem(DEFAULT_UNAJMIO_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].br_fakture").value(hasItem(DEFAULT_BR_FAKTURE.toString())))
                .andExpect(jsonPath("$.[*].zarada").value(hasItem(DEFAULT_ZARADA)))
                .andExpect(jsonPath("$.[*].posecenost").value(hasItem(DEFAULT_POSECENOST)))
                .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())))
                .andExpect(jsonPath("$.[*].napomene").value(hasItem(DEFAULT_NAPOMENE.intValue())));
    }

    @Test
    @Transactional
    public void getUGaleriji() throws Exception {
        // Initialize the database
        uGalerijiRepository.saveAndFlush(uGaleriji);

        // Get the uGaleriji
        restUGalerijiMockMvc.perform(get("/api/u-galerijis/{id}", uGaleriji.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(uGaleriji.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.poslovna_godina").value(DEFAULT_POSLOVNA_GODINA))
            .andExpect(jsonPath("$.kontakt_ime").value(DEFAULT_KONTAKT_IME.toString()))
            .andExpect(jsonPath("$.kontakt_prz").value(DEFAULT_KONTAKT_PRZ.toString()))
            .andExpect(jsonPath("$.kontakt_broj").value(DEFAULT_KONTAKT_BROJ))
            .andExpect(jsonPath("$.kontakt_email").value(DEFAULT_KONTAKT_EMAIL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.moderator_ime").value(DEFAULT_MODERATOR_IME.toString()))
            .andExpect(jsonPath("$.moderator_prz").value(DEFAULT_MODERATOR_PRZ.toString()))
            .andExpect(jsonPath("$.moderator_broj").value(DEFAULT_MODERATOR_BROJ.toString()))
            .andExpect(jsonPath("$.trosak").value(DEFAULT_TROSAK))
            .andExpect(jsonPath("$.unajmio_ime").value(DEFAULT_UNAJMIO_IME.toString()))
            .andExpect(jsonPath("$.unajmio_prz").value(DEFAULT_UNAJMIO_PRZ.toString()))
            .andExpect(jsonPath("$.unajmio_email").value(DEFAULT_UNAJMIO_EMAIL.toString()))
            .andExpect(jsonPath("$.br_fakture").value(DEFAULT_BR_FAKTURE.toString()))
            .andExpect(jsonPath("$.zarada").value(DEFAULT_ZARADA))
            .andExpect(jsonPath("$.posecenost").value(DEFAULT_POSECENOST))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()))
            .andExpect(jsonPath("$.napomene").value(DEFAULT_NAPOMENE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUGaleriji() throws Exception {
        // Get the uGaleriji
        restUGalerijiMockMvc.perform(get("/api/u-galerijis/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUGaleriji() throws Exception {
        // Initialize the database
        uGalerijiRepository.saveAndFlush(uGaleriji);
        int databaseSizeBeforeUpdate = uGalerijiRepository.findAll().size();

        // Update the uGaleriji
        UGaleriji updatedUGaleriji = new UGaleriji();
        updatedUGaleriji.setId(uGaleriji.getId());
        updatedUGaleriji.setNaziv(UPDATED_NAZIV);
        updatedUGaleriji.setDatum(UPDATED_DATUM);
        updatedUGaleriji.setPoslovna_godina(UPDATED_POSLOVNA_GODINA);
        updatedUGaleriji.setKontakt_ime(UPDATED_KONTAKT_IME);
        updatedUGaleriji.setKontakt_prz(UPDATED_KONTAKT_PRZ);
        updatedUGaleriji.setKontakt_broj(UPDATED_KONTAKT_BROJ);
        updatedUGaleriji.setKontakt_email(UPDATED_KONTAKT_EMAIL);
        updatedUGaleriji.setStatus(UPDATED_STATUS);
        updatedUGaleriji.setModerator_ime(UPDATED_MODERATOR_IME);
        updatedUGaleriji.setModerator_prz(UPDATED_MODERATOR_PRZ);
        updatedUGaleriji.setModerator_broj(UPDATED_MODERATOR_BROJ);
        updatedUGaleriji.setTrosak(UPDATED_TROSAK);
        updatedUGaleriji.setUnajmio_ime(UPDATED_UNAJMIO_IME);
        updatedUGaleriji.setUnajmio_prz(UPDATED_UNAJMIO_PRZ);
        updatedUGaleriji.setUnajmio_email(UPDATED_UNAJMIO_EMAIL);
        updatedUGaleriji.setBr_fakture(UPDATED_BR_FAKTURE);
        updatedUGaleriji.setZarada(UPDATED_ZARADA);
        updatedUGaleriji.setPosecenost(UPDATED_POSECENOST);
        updatedUGaleriji.setTip(UPDATED_TIP);
        updatedUGaleriji.setNapomene(UPDATED_NAPOMENE);

        restUGalerijiMockMvc.perform(put("/api/u-galerijis")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUGaleriji)))
                .andExpect(status().isOk());

        // Validate the UGaleriji in the database
        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeUpdate);
        UGaleriji testUGaleriji = uGalerijis.get(uGalerijis.size() - 1);
        assertThat(testUGaleriji.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testUGaleriji.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testUGaleriji.getPoslovna_godina()).isEqualTo(UPDATED_POSLOVNA_GODINA);
        assertThat(testUGaleriji.getKontakt_ime()).isEqualTo(UPDATED_KONTAKT_IME);
        assertThat(testUGaleriji.getKontakt_prz()).isEqualTo(UPDATED_KONTAKT_PRZ);
        assertThat(testUGaleriji.getKontakt_broj()).isEqualTo(UPDATED_KONTAKT_BROJ);
        assertThat(testUGaleriji.getKontakt_email()).isEqualTo(UPDATED_KONTAKT_EMAIL);
        assertThat(testUGaleriji.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUGaleriji.getModerator_ime()).isEqualTo(UPDATED_MODERATOR_IME);
        assertThat(testUGaleriji.getModerator_prz()).isEqualTo(UPDATED_MODERATOR_PRZ);
        assertThat(testUGaleriji.getModerator_broj()).isEqualTo(UPDATED_MODERATOR_BROJ);
        assertThat(testUGaleriji.getTrosak()).isEqualTo(UPDATED_TROSAK);
        assertThat(testUGaleriji.getUnajmio_ime()).isEqualTo(UPDATED_UNAJMIO_IME);
        assertThat(testUGaleriji.getUnajmio_prz()).isEqualTo(UPDATED_UNAJMIO_PRZ);
        assertThat(testUGaleriji.getUnajmio_email()).isEqualTo(UPDATED_UNAJMIO_EMAIL);
        assertThat(testUGaleriji.getBr_fakture()).isEqualTo(UPDATED_BR_FAKTURE);
        assertThat(testUGaleriji.getZarada()).isEqualTo(UPDATED_ZARADA);
        assertThat(testUGaleriji.getPosecenost()).isEqualTo(UPDATED_POSECENOST);
        assertThat(testUGaleriji.getTip()).isEqualTo(UPDATED_TIP);
        assertThat(testUGaleriji.getNapomene()).isEqualTo(UPDATED_NAPOMENE);
    }

    @Test
    @Transactional
    public void deleteUGaleriji() throws Exception {
        // Initialize the database
        uGalerijiRepository.saveAndFlush(uGaleriji);
        int databaseSizeBeforeDelete = uGalerijiRepository.findAll().size();

        // Get the uGaleriji
        restUGalerijiMockMvc.perform(delete("/api/u-galerijis/{id}", uGaleriji.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UGaleriji> uGalerijis = uGalerijiRepository.findAll();
        assertThat(uGalerijis).hasSize(databaseSizeBeforeDelete - 1);
    }
}
