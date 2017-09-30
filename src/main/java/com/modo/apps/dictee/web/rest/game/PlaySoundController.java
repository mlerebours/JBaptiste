package com.modo.apps.dictee.web.rest.game;

import com.codahale.metrics.annotation.Timed;
import com.modo.apps.dictee.config.ApplicationProperties;
import com.modo.apps.dictee.domain.Question;
import com.modo.apps.dictee.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * REST controller for playing sound file.
 */
@RestController
@RequestMapping("/api/sound")
public class PlaySoundController {

    private final Logger log = LoggerFactory.getLogger(PlaySoundController.class);

    //private static final String audioFilesFolder = "Z:\\Java_webapps\\audio_files\\mp3\\";

    private final QuestionService questionService;
    private final ApplicationProperties applicationProperties;

    public PlaySoundController(QuestionService questionService, ApplicationProperties applicationProperties) {
        this.questionService = questionService;
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        log.info("Check MP3 files for questions");

        for (Question q : questionService.findAll()) {
            if (!StringUtils.isEmpty(q.getSoundfile()) &&
                !new File(applicationProperties.getAudio().getFolder() + q.getSoundfile()).exists()) {
                log.warn("Sound file not found for " + q);
                q.setSoundfile(StringUtils.EMPTY);
            }

            if (StringUtils.isEmpty(q.getSoundfile())) {
                questionService.save(q);
            }
        }
    }

    /**
     * GET  /dictee : get all the dictee.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dictees in body
     */
    @GetMapping(value = "/play", produces = {"audio/mpeg"})
    @Timed
    public ResponseEntity<byte[]> sendMp3File(String word) {
        log.debug("REST request file for word " + word);
        Path path = Paths.get(applicationProperties.getAudio().getFolder() + word + ".mp3");
        try {
            byte[] bytes = Files.readAllBytes(path);
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            return responseEntity;
        } catch (IOException e) {
            log.error("Read file error for " + word, e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET  /dictee : get all the dictee.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dictees in body
     */
    @GetMapping(value = "/play/{word}.mp3", produces = {"audio/mpeg"})
    @Timed
    public ResponseEntity<byte[]> sendMp3File2(@PathVariable String word) {
        log.debug("REST request file for word " + word);
        Path path = Paths.get("C:\\_SRC\\JBaptiste\\src\\main\\webapp\\content\\audio\\words\\" + word + ".mp3");
        try {
            byte[] bytes = Files.readAllBytes(path);
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            return responseEntity;
        } catch (IOException e) {
            log.error("Read file error for " + word, e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
