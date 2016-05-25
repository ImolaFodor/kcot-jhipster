package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Sediste;
import com.mycompany.myapp.repository.SedisteRepository;
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
 * REST controller for managing Sediste.
 */
@RestController
@RequestMapping("/api")
public class SedisteResource {

    private final Logger log = LoggerFactory.getLogger(SedisteResource.class);
        
    @Inject
    private SedisteRepository sedisteRepository;
    
    /**
     * POST  /sedistes : Create a new sediste.
     *
     * @param sediste the sediste to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sediste, or with status 400 (Bad Request) if the sediste has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sedistes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sediste> createSediste(@Valid @RequestBody Sediste sediste) throws URISyntaxException {
        log.debug("REST request to save Sediste : {}", sediste);
        if (sediste.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sediste", "idexists", "A new sediste cannot already have an ID")).body(null);
        }
        Sediste result = sedisteRepository.save(sediste);
        return ResponseEntity.created(new URI("/api/sedistes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sediste", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sedistes : Updates an existing sediste.
     *
     * @param sediste the sediste to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sediste,
     * or with status 400 (Bad Request) if the sediste is not valid,
     * or with status 500 (Internal Server Error) if the sediste couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sedistes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sediste> updateSediste(@Valid @RequestBody Sediste sediste) throws URISyntaxException {
        log.debug("REST request to update Sediste : {}", sediste);
        if (sediste.getId() == null) {
            return createSediste(sediste);
        }
        Sediste result = sedisteRepository.save(sediste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sediste", sediste.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sedistes : get all the sedistes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sedistes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/sedistes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Sediste>> getAllSedistes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Sedistes");
        Page<Sediste> page = sedisteRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sedistes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sedistes/:id : get the "id" sediste.
     *
     * @param id the id of the sediste to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sediste, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sedistes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sediste> getSediste(@PathVariable Long id) {
        log.debug("REST request to get Sediste : {}", id);
        Sediste sediste = sedisteRepository.findOne(id);
        return Optional.ofNullable(sediste)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sedistes/:id : delete the "id" sediste.
     *
     * @param id the id of the sediste to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sedistes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSediste(@PathVariable Long id) {
        log.debug("REST request to delete Sediste : {}", id);
        sedisteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sediste", id.toString())).build();
    }

}
