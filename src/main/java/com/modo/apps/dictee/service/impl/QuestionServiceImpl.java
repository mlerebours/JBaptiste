package com.modo.apps.dictee.service.impl;

import com.modo.apps.dictee.service.QuestionService;
import com.modo.apps.dictee.domain.Question;
import com.modo.apps.dictee.repository.QuestionRepository;
import com.modo.apps.dictee.service.TextToSpeech;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Question.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;
    private final TextToSpeech textToSpeech;

    public QuestionServiceImpl(QuestionRepository questionRepository, TextToSpeech textToSpeech) {
        this.questionRepository = questionRepository;
        this.textToSpeech = textToSpeech;
    }

    /**
     * Save a question.
     *
     * @param question the entity to save
     * @return the persisted entity
     */
    @Override
    public Question save(Question question) {
        log.debug("Request to save Question : {}", question);
        if (StringUtils.isEmpty(question.getSoundfile())) {
            log.info("No sound file for " + question);
            String fileForWord = this.textToSpeech.createFileForWord(question.getWord());
            question.setSoundfile(fileForWord);
        }
        return questionRepository.save(question);
    }



    /**
     *  Get all the questions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll();
    }

    /**
     *  Get one question by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Question findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findOne(id);
    }

    /**
     *  Delete the  question by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.delete(id);
    }
}
