package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Gost;
import com.mycompany.myapp.repository.GostRepository;
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
 * REST controller for managing Gost.
 */
@RestController
@RequestMapping("/api")
public class GostResource {

    private final Logger log = LoggerFactory.getLogger(GostResource.class);
        
    @Inject
    private GostRepository gostRepository;
    
    /**
     * POST  /gosts : Create a new gost.
     *
     * @param gost the gost to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gost, or with status 400 (Bad Request) if the gost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gosts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gost> createGost(@Valid @RequestBody Gost gost) throws URISyntaxException {
        log.debug("REST request to save Gost : {}", gost);
        if (gost.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gost", "idexists", "A new gost cannot already have an ID")).body(null);
        }
        Gost result = gostRepository.save(gost);
        return ResponseEntity.created(new URI("/api/gosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gost", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gosts : Updates an existing gost.
     *
     * @param gost the gost to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gost,
     * or with status 400 (Bad Request) if the gost is not valid,
     * or with status 500 (Internal Server Error) if the gost couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gosts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gost> updateGost(@Valid @RequestBody Gost gost) throws URISyntaxException {
        log.debug("REST request to update Gost : {}", gost);
        if (gost.getId() == null) {
            return createGost(gost);
        }
        Gost result = gostRepository.save(gost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gost", gost.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gosts : get all the gosts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gosts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/gosts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Gost>> getAllGosts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Gosts");
        Page<Gost> page = gostRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gosts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gosts/:id : get the "id" gost.
     *
     * @param id the id of the gost to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gost, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/gosts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gost> getGost(@PathVariable Long id) {
        log.debug("REST request to get Gost : {}", id);
        Gost gost = gostRepository.findOne(id);
        return Optional.ofNullable(gost)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gosts/:id : delete the "id" gost.
     *
     * @param id the id of the gost to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/gosts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGost(@PathVariable Long id) {
        log.debug("REST request to delete Gost : {}", id);
        gostRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gost", id.toString())).build();
    }

}
