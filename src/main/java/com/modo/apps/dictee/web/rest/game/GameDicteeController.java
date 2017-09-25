package com.modo.apps.dictee.web.rest.game;

import com.codahale.metrics.annotation.Timed;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.modo.apps.dictee.domain.Dictee;
import com.modo.apps.dictee.domain.Question;
import com.modo.apps.dictee.service.DicteeService;
import com.modo.apps.dictee.service.QuestionService;
import com.modo.apps.dictee.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * REST controller for managing Dictee.
 */
@RestController
@RequestMapping("/api/game")
public class GameDicteeController {

    private final Logger log = LoggerFactory.getLogger(GameDicteeController.class);

    private final DicteeService dicteeService;

    private final Cache<Long, List<Question>> cache;

    public GameDicteeController(DicteeService dicteeService) {
        this.dicteeService = dicteeService;

        //Creation d'un cache classique avec des String comme clefs et valeurs
        cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES) // TTL
            .build();
    }

    private Question getRandomQuestionForDictee(Long dicteeId) {
        List<Question> myValue = cache.getIfPresent(dicteeId);
        if (myValue == null || myValue.isEmpty()) {
            log.info("Load questions for dictee ID=" + dicteeId);
            Dictee dictee = dicteeService.findOne(dicteeId);
            if (dictee != null) {
                myValue = new ArrayList<>(dictee.getWords());
                cache.put(dicteeId, myValue);
            }
            else {
                log.error("Dictee not found for ID=" + dicteeId);
            }
        }

        if (myValue != null && !myValue.isEmpty()) {
            Random randomizer = new Random();
            Question question = myValue.get(randomizer.nextInt(myValue.size()));
            myValue.remove(question);
            return question;
        }

        return null;
    }

    /**
     * GET  /dictee : get all the dictee.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dictees in body
     */
    @GetMapping("/dictee/question")
    @Timed
    public Question getQuestionForDictee(Long dicteeId) {
        log.debug("REST request to get question for Dictee");
        return getRandomQuestionForDictee(dicteeId);
    }
}
