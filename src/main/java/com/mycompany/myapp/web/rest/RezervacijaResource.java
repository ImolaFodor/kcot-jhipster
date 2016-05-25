package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Rezervacija;
import com.mycompany.myapp.repository.RezervacijaRepository;
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
 * REST controller for managing Rezervacija.
 */
@RestController
@RequestMapping("/api")
public class RezervacijaResource {

    private final Logger log = LoggerFactory.getLogger(RezervacijaResource.class);
        
    @Inject
    private RezervacijaRepository rezervacijaRepository;
    
    /**
     * POST  /rezervacijas : Create a new rezervacija.
     *
     * @param rezervacija the rezervacija to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rezervacija, or with status 400 (Bad Request) if the rezervacija has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rezervacijas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rezervacija> createRezervacija(@RequestBody Rezervacija rezervacija) throws URISyntaxException {
        log.debug("REST request to save Rezervacija : {}", rezervacija);
        if (rezervacija.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rezervacija", "idexists", "A new rezervacija cannot already have an ID")).body(null);
        }
        Rezervacija result = rezervacijaRepository.save(rezervacija);
        return ResponseEntity.created(new URI("/api/rezervacijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rezervacija", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rezervacijas : Updates an existing rezervacija.
     *
     * @param rezervacija the rezervacija to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rezervacija,
     * or with status 400 (Bad Request) if the rezervacija is not valid,
     * or with status 500 (Internal Server Error) if the rezervacija couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rezervacijas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rezervacija> updateRezervacija(@RequestBody Rezervacija rezervacija) throws URISyntaxException {
        log.debug("REST request to update Rezervacija : {}", rezervacija);
        if (rezervacija.getId() == null) {
            return createRezervacija(rezervacija);
        }
        Rezervacija result = rezervacijaRepository.save(rezervacija);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rezervacija", rezervacija.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rezervacijas : get all the rezervacijas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rezervacijas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rezervacijas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Rezervacija>> getAllRezervacijas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Rezervacijas");
        Page<Rezervacija> page = rezervacijaRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rezervacijas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rezervacijas/:id : get the "id" rezervacija.
     *
     * @param id the id of the rezervacija to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rezervacija, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rezervacijas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rezervacija> getRezervacija(@PathVariable Long id) {
        log.debug("REST request to get Rezervacija : {}", id);
        Rezervacija rezervacija = rezervacijaRepository.findOne(id);
        return Optional.ofNullable(rezervacija)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rezervacijas/:id : delete the "id" rezervacija.
     *
     * @param id the id of the rezervacija to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rezervacijas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRezervacija(@PathVariable Long id) {
        log.debug("REST request to delete Rezervacija : {}", id);
        rezervacijaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rezervacija", id.toString())).build();
    }

}
