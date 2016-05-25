package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Zaposleni;
import com.mycompany.myapp.repository.ZaposleniRepository;
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
 * REST controller for managing Zaposleni.
 */
@RestController
@RequestMapping("/api")
public class ZaposleniResource {

    private final Logger log = LoggerFactory.getLogger(ZaposleniResource.class);
        
    @Inject
    private ZaposleniRepository zaposleniRepository;
    
    /**
     * POST  /zaposlenis : Create a new zaposleni.
     *
     * @param zaposleni the zaposleni to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zaposleni, or with status 400 (Bad Request) if the zaposleni has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/zaposlenis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Zaposleni> createZaposleni(@Valid @RequestBody Zaposleni zaposleni) throws URISyntaxException {
        log.debug("REST request to save Zaposleni : {}", zaposleni);
        if (zaposleni.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("zaposleni", "idexists", "A new zaposleni cannot already have an ID")).body(null);
        }
        Zaposleni result = zaposleniRepository.save(zaposleni);
        return ResponseEntity.created(new URI("/api/zaposlenis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("zaposleni", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zaposlenis : Updates an existing zaposleni.
     *
     * @param zaposleni the zaposleni to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zaposleni,
     * or with status 400 (Bad Request) if the zaposleni is not valid,
     * or with status 500 (Internal Server Error) if the zaposleni couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/zaposlenis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Zaposleni> updateZaposleni(@Valid @RequestBody Zaposleni zaposleni) throws URISyntaxException {
        log.debug("REST request to update Zaposleni : {}", zaposleni);
        if (zaposleni.getId() == null) {
            return createZaposleni(zaposleni);
        }
        Zaposleni result = zaposleniRepository.save(zaposleni);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("zaposleni", zaposleni.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zaposlenis : get all the zaposlenis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of zaposlenis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/zaposlenis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Zaposleni>> getAllZaposlenis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Zaposlenis");
        Page<Zaposleni> page = zaposleniRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zaposlenis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zaposlenis/:id : get the "id" zaposleni.
     *
     * @param id the id of the zaposleni to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zaposleni, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/zaposlenis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Zaposleni> getZaposleni(@PathVariable Long id) {
        log.debug("REST request to get Zaposleni : {}", id);
        Zaposleni zaposleni = zaposleniRepository.findOne(id);
        return Optional.ofNullable(zaposleni)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /zaposlenis/:id : delete the "id" zaposleni.
     *
     * @param id the id of the zaposleni to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/zaposlenis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteZaposleni(@PathVariable Long id) {
        log.debug("REST request to delete Zaposleni : {}", id);
        zaposleniRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("zaposleni", id.toString())).build();
    }

}
