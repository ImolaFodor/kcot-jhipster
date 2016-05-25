package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DnevniPazar;
import com.mycompany.myapp.repository.DnevniPazarRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DnevniPazar.
 */
@RestController
@RequestMapping("/api")
public class DnevniPazarResource {

    private final Logger log = LoggerFactory.getLogger(DnevniPazarResource.class);
        
    @Inject
    private DnevniPazarRepository dnevniPazarRepository;
    
    /**
     * POST  /dnevni-pazars : Create a new dnevniPazar.
     *
     * @param dnevniPazar the dnevniPazar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dnevniPazar, or with status 400 (Bad Request) if the dnevniPazar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dnevni-pazars",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DnevniPazar> createDnevniPazar(@RequestBody DnevniPazar dnevniPazar) throws URISyntaxException {
        log.debug("REST request to save DnevniPazar : {}", dnevniPazar);
        if (dnevniPazar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dnevniPazar", "idexists", "A new dnevniPazar cannot already have an ID")).body(null);
        }
        DnevniPazar result = dnevniPazarRepository.save(dnevniPazar);
        return ResponseEntity.created(new URI("/api/dnevni-pazars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dnevniPazar", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dnevni-pazars : Updates an existing dnevniPazar.
     *
     * @param dnevniPazar the dnevniPazar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dnevniPazar,
     * or with status 400 (Bad Request) if the dnevniPazar is not valid,
     * or with status 500 (Internal Server Error) if the dnevniPazar couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dnevni-pazars",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DnevniPazar> updateDnevniPazar(@RequestBody DnevniPazar dnevniPazar) throws URISyntaxException {
        log.debug("REST request to update DnevniPazar : {}", dnevniPazar);
        if (dnevniPazar.getId() == null) {
            return createDnevniPazar(dnevniPazar);
        }
        DnevniPazar result = dnevniPazarRepository.save(dnevniPazar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dnevniPazar", dnevniPazar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dnevni-pazars : get all the dnevniPazars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dnevniPazars in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/dnevni-pazars",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DnevniPazar>> getAllDnevniPazars(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DnevniPazars");
        Page<DnevniPazar> page = dnevniPazarRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dnevni-pazars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dnevni-pazars/:id : get the "id" dnevniPazar.
     *
     * @param id the id of the dnevniPazar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dnevniPazar, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dnevni-pazars/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DnevniPazar> getDnevniPazar(@PathVariable Long id) {
        log.debug("REST request to get DnevniPazar : {}", id);
        DnevniPazar dnevniPazar = dnevniPazarRepository.findOne(id);
        return Optional.ofNullable(dnevniPazar)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dnevni-pazars/:id : delete the "id" dnevniPazar.
     *
     * @param id the id of the dnevniPazar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dnevni-pazars/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDnevniPazar(@PathVariable Long id) {
        log.debug("REST request to delete DnevniPazar : {}", id);
        dnevniPazarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dnevniPazar", id.toString())).build();
    }

}
