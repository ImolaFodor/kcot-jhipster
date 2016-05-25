package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Skola;
import com.mycompany.myapp.repository.SkolaRepository;
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
 * REST controller for managing Skola.
 */
@RestController
@RequestMapping("/api")
public class SkolaResource {

    private final Logger log = LoggerFactory.getLogger(SkolaResource.class);
        
    @Inject
    private SkolaRepository skolaRepository;
    
    /**
     * POST  /skolas : Create a new skola.
     *
     * @param skola the skola to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skola, or with status 400 (Bad Request) if the skola has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/skolas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skola> createSkola(@Valid @RequestBody Skola skola) throws URISyntaxException {
        log.debug("REST request to save Skola : {}", skola);
        if (skola.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("skola", "idexists", "A new skola cannot already have an ID")).body(null);
        }
        Skola result = skolaRepository.save(skola);
        return ResponseEntity.created(new URI("/api/skolas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("skola", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skolas : Updates an existing skola.
     *
     * @param skola the skola to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skola,
     * or with status 400 (Bad Request) if the skola is not valid,
     * or with status 500 (Internal Server Error) if the skola couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/skolas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skola> updateSkola(@Valid @RequestBody Skola skola) throws URISyntaxException {
        log.debug("REST request to update Skola : {}", skola);
        if (skola.getId() == null) {
            return createSkola(skola);
        }
        Skola result = skolaRepository.save(skola);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("skola", skola.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skolas : get all the skolas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skolas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/skolas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Skola>> getAllSkolas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Skolas");
        Page<Skola> page = skolaRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skolas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /skolas/:id : get the "id" skola.
     *
     * @param id the id of the skola to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skola, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/skolas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skola> getSkola(@PathVariable Long id) {
        log.debug("REST request to get Skola : {}", id);
        Skola skola = skolaRepository.findOne(id);
        return Optional.ofNullable(skola)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /skolas/:id : delete the "id" skola.
     *
     * @param id the id of the skola to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/skolas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSkola(@PathVariable Long id) {
        log.debug("REST request to delete Skola : {}", id);
        skolaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("skola", id.toString())).build();
    }

}
