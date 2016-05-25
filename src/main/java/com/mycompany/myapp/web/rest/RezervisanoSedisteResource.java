package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.RezervisanoSediste;
import com.mycompany.myapp.repository.RezervisanoSedisteRepository;
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
 * REST controller for managing RezervisanoSediste.
 */
@RestController
@RequestMapping("/api")
public class RezervisanoSedisteResource {

    private final Logger log = LoggerFactory.getLogger(RezervisanoSedisteResource.class);
        
    @Inject
    private RezervisanoSedisteRepository rezervisanoSedisteRepository;
    
    /**
     * POST  /rezervisano-sedistes : Create a new rezervisanoSediste.
     *
     * @param rezervisanoSediste the rezervisanoSediste to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rezervisanoSediste, or with status 400 (Bad Request) if the rezervisanoSediste has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rezervisano-sedistes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RezervisanoSediste> createRezervisanoSediste(@Valid @RequestBody RezervisanoSediste rezervisanoSediste) throws URISyntaxException {
        log.debug("REST request to save RezervisanoSediste : {}", rezervisanoSediste);
        if (rezervisanoSediste.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rezervisanoSediste", "idexists", "A new rezervisanoSediste cannot already have an ID")).body(null);
        }
        RezervisanoSediste result = rezervisanoSedisteRepository.save(rezervisanoSediste);
        return ResponseEntity.created(new URI("/api/rezervisano-sedistes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rezervisanoSediste", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rezervisano-sedistes : Updates an existing rezervisanoSediste.
     *
     * @param rezervisanoSediste the rezervisanoSediste to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rezervisanoSediste,
     * or with status 400 (Bad Request) if the rezervisanoSediste is not valid,
     * or with status 500 (Internal Server Error) if the rezervisanoSediste couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rezervisano-sedistes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RezervisanoSediste> updateRezervisanoSediste(@Valid @RequestBody RezervisanoSediste rezervisanoSediste) throws URISyntaxException {
        log.debug("REST request to update RezervisanoSediste : {}", rezervisanoSediste);
        if (rezervisanoSediste.getId() == null) {
            return createRezervisanoSediste(rezervisanoSediste);
        }
        RezervisanoSediste result = rezervisanoSedisteRepository.save(rezervisanoSediste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rezervisanoSediste", rezervisanoSediste.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rezervisano-sedistes : get all the rezervisanoSedistes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rezervisanoSedistes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rezervisano-sedistes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RezervisanoSediste>> getAllRezervisanoSedistes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RezervisanoSedistes");
        Page<RezervisanoSediste> page = rezervisanoSedisteRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rezervisano-sedistes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rezervisano-sedistes/:id : get the "id" rezervisanoSediste.
     *
     * @param id the id of the rezervisanoSediste to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rezervisanoSediste, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rezervisano-sedistes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RezervisanoSediste> getRezervisanoSediste(@PathVariable Long id) {
        log.debug("REST request to get RezervisanoSediste : {}", id);
        RezervisanoSediste rezervisanoSediste = rezervisanoSedisteRepository.findOne(id);
        return Optional.ofNullable(rezervisanoSediste)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rezervisano-sedistes/:id : delete the "id" rezervisanoSediste.
     *
     * @param id the id of the rezervisanoSediste to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rezervisano-sedistes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRezervisanoSediste(@PathVariable Long id) {
        log.debug("REST request to delete RezervisanoSediste : {}", id);
        rezervisanoSedisteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rezervisanoSediste", id.toString())).build();
    }

}
