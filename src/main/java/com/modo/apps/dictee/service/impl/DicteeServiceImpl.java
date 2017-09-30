package com.modo.apps.dictee.service.impl;

import com.modo.apps.dictee.service.DicteeService;
import com.modo.apps.dictee.domain.Dictee;
import com.modo.apps.dictee.repository.DicteeRepository;
import com.modo.apps.dictee.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Dictee.
 */
@Service
@Transactional
public class DicteeServiceImpl implements DicteeService{

    private final Logger log = LoggerFactory.getLogger(DicteeServiceImpl.class);

    private final DicteeRepository dicteeRepository;
    private final QuestionService questionService;

    public DicteeServiceImpl(DicteeRepository dicteeRepository, QuestionService questionService) {
        this.dicteeRepository = dicteeRepository;
        this.questionService = questionService;
    }

    /**
     * Save a dictee.
     *
     * @param dictee the entity to save
     * @return the persisted entity
     */
    @Override
    public Dictee save(Dictee dictee) {
        log.debug("Request to save Dictee : {}", dictee);
        return dicteeRepository.save(dictee);
    }

    /**
     *  Get all the dictees.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Dictee> findAll() {
        log.debug("Request to get all Dictees");
        return dicteeRepository.findAllWithEagerRelationships();
    }

    /**
     *  Get one dictee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Dictee findOne(Long id) {
        log.debug("Request to get Dictee : {}", id);
        return dicteeRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  dictee by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dictee : {}", id);
        dicteeRepository.delete(id);
    }
}
