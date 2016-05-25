package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Donirana_dela;
import com.mycompany.myapp.repository.Donirana_delaRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Donirana_dela.
 */
@RestController
@RequestMapping("/api")
public class Donirana_delaResource {

    private final Logger log = LoggerFactory.getLogger(Donirana_delaResource.class);
        
    @Inject
    private Donirana_delaRepository donirana_delaRepository;
    
    /**
     * POST  /donirana-delas : Create a new donirana_dela.
     *
     * @param donirana_dela the donirana_dela to create
     * @return the ResponseEntity with status 201 (Created) and with body the new donirana_dela, or with status 400 (Bad Request) if the donirana_dela has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/donirana-delas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Donirana_dela> createDonirana_dela(@Valid @RequestBody Donirana_dela donirana_dela) throws URISyntaxException {
        log.debug("REST request to save Donirana_dela : {}", donirana_dela);
        if (donirana_dela.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("donirana_dela", "idexists", "A new donirana_dela cannot already have an ID")).body(null);
        }
        Donirana_dela result = donirana_delaRepository.save(donirana_dela);
        return ResponseEntity.created(new URI("/api/donirana-delas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("donirana_dela", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /donirana-delas : Updates an existing donirana_dela.
     *
     * @param donirana_dela the donirana_dela to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated donirana_dela,
     * or with status 400 (Bad Request) if the donirana_dela is not valid,
     * or with status 500 (Internal Server Error) if the donirana_dela couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/donirana-delas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Donirana_dela> updateDonirana_dela(@Valid @RequestBody Donirana_dela donirana_dela) throws URISyntaxException {
        log.debug("REST request to update Donirana_dela : {}", donirana_dela);
        if (donirana_dela.getId() == null) {
            return createDonirana_dela(donirana_dela);
        }
        Donirana_dela result = donirana_delaRepository.save(donirana_dela);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("donirana_dela", donirana_dela.getId().toString()))
            .body(result);
    }

    /**
     * GET  /donirana-delas : get all the donirana_delas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of donirana_delas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/donirana-delas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Donirana_dela>> getAllDonirana_delas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Donirana_delas");
        Page<Donirana_dela> page = donirana_delaRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/donirana-delas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /donirana-delas/:id : get the "id" donirana_dela.
     *
     * @param id the id of the donirana_dela to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donirana_dela, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donirana-delas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Donirana_dela> getDonirana_dela(@PathVariable Long id) {
        log.debug("REST request to get Donirana_dela : {}", id);
        Donirana_dela donirana_dela = donirana_delaRepository.findOne(id);
        return Optional.ofNullable(donirana_dela)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /donirana-delas/:id : delete the "id" donirana_dela.
     *
     * @param id the id of the donirana_dela to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/donirana-delas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDonirana_dela(@PathVariable Long id) {
        log.debug("REST request to delete Donirana_dela : {}", id);
        donirana_delaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("donirana_dela", id.toString())).build();
    }

}
