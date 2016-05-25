package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Pozvani;
import com.mycompany.myapp.repository.PozvaniRepository;
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
 * REST controller for managing Pozvani.
 */
@RestController
@RequestMapping("/api")
public class PozvaniResource {

    private final Logger log = LoggerFactory.getLogger(PozvaniResource.class);
        
    @Inject
    private PozvaniRepository pozvaniRepository;
    
    /**
     * POST  /pozvanis : Create a new pozvani.
     *
     * @param pozvani the pozvani to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pozvani, or with status 400 (Bad Request) if the pozvani has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pozvanis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pozvani> createPozvani(@Valid @RequestBody Pozvani pozvani) throws URISyntaxException {
        log.debug("REST request to save Pozvani : {}", pozvani);
        if (pozvani.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pozvani", "idexists", "A new pozvani cannot already have an ID")).body(null);
        }
        Pozvani result = pozvaniRepository.save(pozvani);
        return ResponseEntity.created(new URI("/api/pozvanis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pozvani", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pozvanis : Updates an existing pozvani.
     *
     * @param pozvani the pozvani to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pozvani,
     * or with status 400 (Bad Request) if the pozvani is not valid,
     * or with status 500 (Internal Server Error) if the pozvani couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pozvanis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pozvani> updatePozvani(@Valid @RequestBody Pozvani pozvani) throws URISyntaxException {
        log.debug("REST request to update Pozvani : {}", pozvani);
        if (pozvani.getId() == null) {
            return createPozvani(pozvani);
        }
        Pozvani result = pozvaniRepository.save(pozvani);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pozvani", pozvani.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pozvanis : get all the pozvanis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pozvanis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/pozvanis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pozvani>> getAllPozvanis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pozvanis");
        Page<Pozvani> page = pozvaniRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pozvanis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pozvanis/:id : get the "id" pozvani.
     *
     * @param id the id of the pozvani to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pozvani, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/pozvanis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pozvani> getPozvani(@PathVariable Long id) {
        log.debug("REST request to get Pozvani : {}", id);
        Pozvani pozvani = pozvaniRepository.findOne(id);
        return Optional.ofNullable(pozvani)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pozvanis/:id : delete the "id" pozvani.
     *
     * @param id the id of the pozvani to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/pozvanis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePozvani(@PathVariable Long id) {
        log.debug("REST request to delete Pozvani : {}", id);
        pozvaniRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pozvani", id.toString())).build();
    }

}
