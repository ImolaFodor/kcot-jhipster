package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.RezervacijaProdaja;
import com.mycompany.myapp.domain.RezervisanoSediste;
import com.mycompany.myapp.domain.Sediste;
import com.mycompany.myapp.repository.RezervacijaProdajaRepository;
import com.mycompany.myapp.repository.RezervisanoSedisteRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RezervacijaProdaja.
 */
@RestController
@RequestMapping("/api")
public class RezervacijaProdajaResource {

    private final Logger log = LoggerFactory.getLogger(RezervacijaProdajaResource.class);

    @Inject
    private RezervacijaProdajaRepository rezervacijaProdajaRepository;
    @Inject
    private RezervisanoSedisteRepository rezervisanoSedisteRepository;

    @Inject
    private SedisteRepository sedisteRepository;

    /**
     * POST  /rezervacija-prodajas : Create a new rezervacijaProdaja.
     *
     * @param rezervacijaProdaja the rezervacijaProdaja to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rezervacijaProdaja, or with status 400 (Bad Request) if the rezervacijaProdaja has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rezervacija-prodajas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RezervacijaProdaja> createRezervacijaProdaja(@Valid @RequestBody RezervacijaProdaja rezervacijaProdaja) throws URISyntaxException {
        log.debug("REST request to save RezervacijaProdaja : {}", rezervacijaProdaja);
        if (rezervacijaProdaja.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rezervacijaProdaja", "idexists", "A new rezervacijaProdaja cannot already have an ID")).body(null);
        }
        RezervacijaProdaja result = rezervacijaProdajaRepository.save(rezervacijaProdaja);

        int kolicina= result.getBroj_karata();

        List<Sediste> sedista= sedisteRepository.findAll();
        List<RezervisanoSediste> rez_sedista= rezervisanoSedisteRepository.findAll();
        List<Sediste> zauzeta_sedista= new ArrayList();

        for(RezervisanoSediste rs: rez_sedista){
            if(rs.getRezsed().getDogadjaj().equals(result.getDogadjaj()))
                zauzeta_sedista.add(rs.getSediste());
            System.out.println(rs.getSediste().getId());
        }

        RezervisanoSediste rezervisanoSediste=null;
        ArrayList<RezervisanoSediste> lrs= new ArrayList();

        for(Sediste s: sedista){
            if(!zauzeta_sedista.contains(s) && (zauzeta_sedista.size()+kolicina)<=sedista.size()) {
                rezervisanoSediste = new RezervisanoSediste(s, result);
                lrs.add(rezervisanoSediste);
                System.out.println(rezervisanoSediste.getSediste().getId());
            }
        }

        for(int i=0; i<kolicina; i++){
            rezervisanoSedisteRepository.save(lrs.get(i));
        }
        return ResponseEntity.created(new URI("/api/rezervacija-prodajas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rezervacijaProdaja", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rezervacija-prodajas : Updates an existing rezervacijaProdaja.
     *
     * @param rezervacijaProdaja the rezervacijaProdaja to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rezervacijaProdaja,
     * or with status 400 (Bad Request) if the rezervacijaProdaja is not valid,
     * or with status 500 (Internal Server Error) if the rezervacijaProdaja couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rezervacija-prodajas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RezervacijaProdaja> updateRezervacijaProdaja(@Valid @RequestBody RezervacijaProdaja rezervacijaProdaja) throws URISyntaxException {
        log.debug("REST request to update RezervacijaProdaja : {}", rezervacijaProdaja);
        if (rezervacijaProdaja.getId() == null) {
            return createRezervacijaProdaja(rezervacijaProdaja);
        }
        RezervacijaProdaja result = rezervacijaProdajaRepository.save(rezervacijaProdaja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rezervacijaProdaja", rezervacijaProdaja.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rezervacija-prodajas : get all the rezervacijaProdajas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rezervacijaProdajas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/rezervacija-prodajas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RezervacijaProdaja>> getAllRezervacijaProdajas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RezervacijaProdajas");
        Page<RezervacijaProdaja> page = rezervacijaProdajaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rezervacija-prodajas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rezervacija-prodajas/:id : get the "id" rezervacijaProdaja.
     *
     * @param id the id of the rezervacijaProdaja to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rezervacijaProdaja, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rezervacija-prodajas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RezervacijaProdaja> getRezervacijaProdaja(@PathVariable Long id) {
        log.debug("REST request to get RezervacijaProdaja : {}", id);
        RezervacijaProdaja rezervacijaProdaja = rezervacijaProdajaRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(rezervacijaProdaja)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rezervacija-prodajas/:id : delete the "id" rezervacijaProdaja.
     *
     * @param id the id of the rezervacijaProdaja to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rezervacija-prodajas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRezervacijaProdaja(@PathVariable Long id) {
        log.debug("REST request to delete RezervacijaProdaja : {}", id);
        rezervacijaProdajaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rezervacijaProdaja", id.toString())).build();
    }

}
