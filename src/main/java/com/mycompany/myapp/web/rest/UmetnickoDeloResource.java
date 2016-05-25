package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.UmetnickoDelo;
import com.mycompany.myapp.repository.UmetnickoDeloRepository;
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
 * REST controller for managing UmetnickoDelo.
 */
@RestController
@RequestMapping("/api")
public class UmetnickoDeloResource {

    private final Logger log = LoggerFactory.getLogger(UmetnickoDeloResource.class);
        
    @Inject
    private UmetnickoDeloRepository umetnickoDeloRepository;
    
    /**
     * POST  /umetnicko-delos : Create a new umetnickoDelo.
     *
     * @param umetnickoDelo the umetnickoDelo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new umetnickoDelo, or with status 400 (Bad Request) if the umetnickoDelo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/umetnicko-delos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmetnickoDelo> createUmetnickoDelo(@Valid @RequestBody UmetnickoDelo umetnickoDelo) throws URISyntaxException {
        log.debug("REST request to save UmetnickoDelo : {}", umetnickoDelo);
        if (umetnickoDelo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("umetnickoDelo", "idexists", "A new umetnickoDelo cannot already have an ID")).body(null);
        }
        UmetnickoDelo result = umetnickoDeloRepository.save(umetnickoDelo);
        return ResponseEntity.created(new URI("/api/umetnicko-delos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umetnickoDelo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umetnicko-delos : Updates an existing umetnickoDelo.
     *
     * @param umetnickoDelo the umetnickoDelo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated umetnickoDelo,
     * or with status 400 (Bad Request) if the umetnickoDelo is not valid,
     * or with status 500 (Internal Server Error) if the umetnickoDelo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/umetnicko-delos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmetnickoDelo> updateUmetnickoDelo(@Valid @RequestBody UmetnickoDelo umetnickoDelo) throws URISyntaxException {
        log.debug("REST request to update UmetnickoDelo : {}", umetnickoDelo);
        if (umetnickoDelo.getId() == null) {
            return createUmetnickoDelo(umetnickoDelo);
        }
        UmetnickoDelo result = umetnickoDeloRepository.save(umetnickoDelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umetnickoDelo", umetnickoDelo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umetnicko-delos : get all the umetnickoDelos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of umetnickoDelos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/umetnicko-delos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmetnickoDelo>> getAllUmetnickoDelos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UmetnickoDelos");
        Page<UmetnickoDelo> page = umetnickoDeloRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umetnicko-delos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umetnicko-delos/:id : get the "id" umetnickoDelo.
     *
     * @param id the id of the umetnickoDelo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the umetnickoDelo, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/umetnicko-delos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmetnickoDelo> getUmetnickoDelo(@PathVariable Long id) {
        log.debug("REST request to get UmetnickoDelo : {}", id);
        UmetnickoDelo umetnickoDelo = umetnickoDeloRepository.findOne(id);
        return Optional.ofNullable(umetnickoDelo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umetnicko-delos/:id : delete the "id" umetnickoDelo.
     *
     * @param id the id of the umetnickoDelo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/umetnicko-delos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmetnickoDelo(@PathVariable Long id) {
        log.debug("REST request to delete UmetnickoDelo : {}", id);
        umetnickoDeloRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umetnickoDelo", id.toString())).build();
    }

}
