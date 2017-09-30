package com.modo.apps.dictee.service.impl;

import com.modo.apps.dictee.config.ApplicationProperties;
import com.modo.apps.dictee.service.TextToSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Service Implementation for managing Dictee.
 */
@Service
@Transactional
public class TextToSpeechImpl implements TextToSpeech {

    private final Logger log = LoggerFactory.getLogger(TextToSpeechImpl.class);

    private final ApplicationProperties applicationProperties;

    public TextToSpeechImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public String createFileForWord(String word) {
        try {
            return runPythonConverter(word);

        } catch (Exception e) {
            log.error("Converter failed for " + word, e);
        }
        return null;
    }


    private String runPythonConverter(String word) throws Exception {
        String script = applicationProperties.getPython().getFolder() + "text_to_mp3.py";

        String outputFolder = applicationProperties.getAudio().getFolder(); //"C:\\_SRC\\JBaptiste\\src\\main\\webapp\\content\\audio";

        String target = applicationProperties.getPython().getName() + " " + script + " -o" + outputFolder + " -w" + word;
        log.info(target);
        Runtime rt = Runtime.getRuntime();

        Process proc = rt.exec(target);
        InputStream is = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buff = new BufferedReader (isr);
        String line = null;
        log.info("<OUTPUT>");

        while ( (line = buff.readLine()) != null)
            log.info(line);

        log.info("</OUTPUT>");
        int exitVal = proc.waitFor();
        log.info("Process exitValue: " + exitVal);
        if (0 == exitVal) {
            return word + ".mp3";
        }
        return null;
    }
}
