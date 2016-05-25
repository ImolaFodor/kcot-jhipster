package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.USali;
import com.mycompany.myapp.repository.USaliRepository;
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
 * REST controller for managing USali.
 */
@RestController
@RequestMapping("/api")
public class USaliResource {

    private final Logger log = LoggerFactory.getLogger(USaliResource.class);
        
    @Inject
    private USaliRepository uSaliRepository;
    
    /**
     * POST  /u-salis : Create a new uSali.
     *
     * @param uSali the uSali to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uSali, or with status 400 (Bad Request) if the uSali has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/u-salis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<USali> createUSali(@Valid @RequestBody USali uSali) throws URISyntaxException {
        log.debug("REST request to save USali : {}", uSali);
        if (uSali.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("uSali", "idexists", "A new uSali cannot already have an ID")).body(null);
        }
        USali result = uSaliRepository.save(uSali);
        return ResponseEntity.created(new URI("/api/u-salis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("uSali", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /u-salis : Updates an existing uSali.
     *
     * @param uSali the uSali to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uSali,
     * or with status 400 (Bad Request) if the uSali is not valid,
     * or with status 500 (Internal Server Error) if the uSali couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/u-salis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<USali> updateUSali(@Valid @RequestBody USali uSali) throws URISyntaxException {
        log.debug("REST request to update USali : {}", uSali);
        if (uSali.getId() == null) {
            return createUSali(uSali);
        }
        USali result = uSaliRepository.save(uSali);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("uSali", uSali.getId().toString()))
            .body(result);
    }

    /**
     * GET  /u-salis : get all the uSalis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uSalis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/u-salis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<USali>> getAllUSalis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of USalis");
        Page<USali> page = uSaliRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/u-salis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /u-salis/:id : get the "id" uSali.
     *
     * @param id the id of the uSali to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uSali, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/u-salis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<USali> getUSali(@PathVariable Long id) {
        log.debug("REST request to get USali : {}", id);
        USali uSali = uSaliRepository.findOne(id);
        return Optional.ofNullable(uSali)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /u-salis/:id : delete the "id" uSali.
     *
     * @param id the id of the uSali to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/u-salis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUSali(@PathVariable Long id) {
        log.debug("REST request to delete USali : {}", id);
        uSaliRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("uSali", id.toString())).build();
    }

}
