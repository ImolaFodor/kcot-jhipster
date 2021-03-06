package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UGaleriji;
import com.mycompany.myapp.domain.enumeration.StatusGal;
import com.mycompany.myapp.repository.UGalerijiRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing UGaleriji.
 */
@RestController
@RequestMapping("/api")
public class UGalerijiResource {

    private final Logger log = LoggerFactory.getLogger(UGalerijiResource.class);

    @Inject
    private UGalerijiRepository uGalerijiRepository;

    /**
     * POST  /u-galerijis : Create a new uGaleriji.
     *
     * @param uGaleriji the uGaleriji to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uGaleriji, or with status 400 (Bad Request) if the uGaleriji has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/u-galerijis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UGaleriji> createUGaleriji(@Valid @RequestBody UGaleriji uGaleriji) throws URISyntaxException {
        log.debug("REST request to save UGaleriji : {}", uGaleriji);
        if (uGaleriji.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("uGaleriji", "idexists", "A new uGaleriji cannot already have an ID")).body(null);
        }
        UGaleriji result = uGalerijiRepository.save(uGaleriji);
        return ResponseEntity.created(new URI("/api/u-galerijis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("uGaleriji", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /u-galerijis : Updates an existing uGaleriji.
     *
     * @param uGaleriji the uGaleriji to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uGaleriji,
     * or with status 400 (Bad Request) if the uGaleriji is not valid,
     * or with status 500 (Internal Server Error) if the uGaleriji couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/u-galerijis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UGaleriji> updateUGaleriji(@Valid @RequestBody UGaleriji uGaleriji) throws URISyntaxException {
        log.debug("REST request to update UGaleriji : {}", uGaleriji);
        if (uGaleriji.getId() == null) {
            return createUGaleriji(uGaleriji);
        }
        UGaleriji result = uGalerijiRepository.save(uGaleriji);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("uGaleriji", uGaleriji.getId().toString()))
            .body(result);
    }

    /**
     * GET  /u-galerijis : get all the uGalerijis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uGalerijis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/u-galerijis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UGaleriji>> getAllUGalerijis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UGalerijis");
        Page<UGaleriji> page = uGalerijiRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/u-galerijis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /u-galerijis/:id : get the "id" uGaleriji.
     *
     * @param id the id of the uGaleriji to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uGaleriji, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/u-galerijis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UGaleriji> getUGaleriji(@PathVariable Long id) {
        log.debug("REST request to get UGaleriji : {}", id);
        UGaleriji uGaleriji = uGalerijiRepository.findOne(id);
        return Optional.ofNullable(uGaleriji)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /u-galerijis/:id : delete the "id" uGaleriji.
     *
     * @param id the id of the uGaleriji to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/u-galerijis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUGaleriji(@PathVariable Long id) {
        log.debug("REST request to delete UGaleriji : {}", id);
        uGalerijiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("uGaleriji", id.toString())).build();
    }

}
