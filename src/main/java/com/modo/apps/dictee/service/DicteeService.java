package com.modo.apps.dictee.service;

import com.modo.apps.dictee.domain.Dictee;
import java.util.List;

/**
 * Service Interface for managing Dictee.
 */
public interface DicteeService {

    /**
     * Save a dictee.
     *
     * @param dictee the entity to save
     * @return the persisted entity
     */
    Dictee save(Dictee dictee);

    /**
     *  Get all the dictees.
     *
     *  @return the list of entities
     */
    List<Dictee> findAll();

    /**
     *  Get the "id" dictee.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Dictee findOne(Long id);

    /**
     *  Delete the "id" dictee.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
