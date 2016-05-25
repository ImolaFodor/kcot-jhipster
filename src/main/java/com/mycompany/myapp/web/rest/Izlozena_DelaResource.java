package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Izlozena_Dela;
import com.mycompany.myapp.repository.Izlozena_DelaRepository;
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
 * REST controller for managing Izlozena_dela.
 */
@RestController
@RequestMapping("/api")
public class Izlozena_DelaResource {

    private final Logger log = LoggerFactory.getLogger(Izlozena_DelaResource.class);

    @Inject
    private Izlozena_DelaRepository izlozena_delaRepository;

    /**
     * POST  /izlozena-delas : Create a new izlozena_dela.
     *
     * @param izlozena_dela the izlozena_dela to create
     * @return the ResponseEntity with status 201 (Created) and with body the new izlozena_dela, or with status 400 (Bad Request) if the izlozena_dela has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/izlozena-delas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Izlozena_Dela> createIzlozena_dela(@Valid @RequestBody Izlozena_Dela izlozena_dela) throws URISyntaxException {
        log.debug("REST request to save Izlozena_dela : {}", izlozena_dela);
        if (izlozena_dela.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("izlozena_dela", "idexists", "A new izlozena_dela cannot already have an ID")).body(null);
        }
        Izlozena_Dela result = izlozena_delaRepository.save(izlozena_dela);
        return ResponseEntity.created(new URI("/api/izlozena-delas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("izlozena_dela", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /izlozena-delas : Updates an existing izlozena_dela.
     *
     * @param izlozena_dela the izlozena_dela to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated izlozena_dela,
     * or with status 400 (Bad Request) if the izlozena_dela is not valid,
     * or with status 500 (Internal Server Error) if the izlozena_dela couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/izlozena-delas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Izlozena_Dela> updateIzlozena_dela(@Valid @RequestBody Izlozena_Dela izlozena_dela) throws URISyntaxException {
        log.debug("REST request to update Izlozena_dela : {}", izlozena_dela);
        if (izlozena_dela.getId() == null) {
            return createIzlozena_dela(izlozena_dela);
        }
        Izlozena_Dela result = izlozena_delaRepository.save(izlozena_dela);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("izlozena_dela", izlozena_dela.getId().toString()))
            .body(result);
    }

    /**
     * GET  /izlozena-delas : get all the izlozena_delas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of izlozena_delas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/izlozena-delas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Izlozena_Dela>> getAllIzlozena_delas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Izlozena_delas");
        Page<Izlozena_Dela> page = izlozena_delaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/izlozena-delas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /izlozena-delas/:id : get the "id" izlozena_dela.
     *
     * @param id the id of the izlozena_dela to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the izlozena_dela, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/izlozena-delas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Izlozena_Dela> getIzlozena_dela(@PathVariable Long id) {
        log.debug("REST request to get Izlozena_dela : {}", id);
        Izlozena_Dela izlozena_dela = izlozena_delaRepository.findOne(id);
        return Optional.ofNullable(izlozena_dela)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /izlozena-delas/:id : delete the "id" izlozena_dela.
     *
     * @param id the id of the izlozena_dela to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/izlozena-delas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIzlozena_dela(@PathVariable Long id) {
        log.debug("REST request to delete Izlozena_dela : {}", id);
        izlozena_delaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("izlozena_dela", id.toString())).build();
    }

}
