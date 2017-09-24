package com.modo.apps.dictee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.modo.apps.dictee.domain.Dictee;
import com.modo.apps.dictee.service.DicteeService;
import com.modo.apps.dictee.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dictee.
 */
@RestController
@RequestMapping("/api")
public class DicteeResource {

    private final Logger log = LoggerFactory.getLogger(DicteeResource.class);

    private static final String ENTITY_NAME = "dictee";

    private final DicteeService dicteeService;

    public DicteeResource(DicteeService dicteeService) {
        this.dicteeService = dicteeService;
    }

    /**
     * POST  /dictees : Create a new dictee.
     *
     * @param dictee the dictee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dictee, or with status 400 (Bad Request) if the dictee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dictees")
    @Timed
    public ResponseEntity<Dictee> createDictee(@RequestBody Dictee dictee) throws URISyntaxException {
        log.debug("REST request to save Dictee : {}", dictee);
        if (dictee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dictee cannot already have an ID")).body(null);
        }
        Dictee result = dicteeService.save(dictee);
        return ResponseEntity.created(new URI("/api/dictees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dictees : Updates an existing dictee.
     *
     * @param dictee the dictee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dictee,
     * or with status 400 (Bad Request) if the dictee is not valid,
     * or with status 500 (Internal Server Error) if the dictee couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dictees")
    @Timed
    public ResponseEntity<Dictee> updateDictee(@RequestBody Dictee dictee) throws URISyntaxException {
        log.debug("REST request to update Dictee : {}", dictee);
        if (dictee.getId() == null) {
            return createDictee(dictee);
        }
        Dictee result = dicteeService.save(dictee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dictee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dictees : get all the dictees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dictees in body
     */
    @GetMapping("/dictees")
    @Timed
    public List<Dictee> getAllDictees() {
        log.debug("REST request to get all Dictees");
        return dicteeService.findAll();
        }

    /**
     * GET  /dictees/:id : get the "id" dictee.
     *
     * @param id the id of the dictee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dictee, or with status 404 (Not Found)
     */
    @GetMapping("/dictees/{id}")
    @Timed
    public ResponseEntity<Dictee> getDictee(@PathVariable Long id) {
        log.debug("REST request to get Dictee : {}", id);
        Dictee dictee = dicteeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dictee));
    }

    /**
     * DELETE  /dictees/:id : delete the "id" dictee.
     *
     * @param id the id of the dictee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dictees/{id}")
    @Timed
    public ResponseEntity<Void> deleteDictee(@PathVariable Long id) {
        log.debug("REST request to delete Dictee : {}", id);
        dicteeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
