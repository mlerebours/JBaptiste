package com.modo.apps.dictee.service.impl;

import com.modo.apps.dictee.config.ApplicationProperties;
import com.modo.apps.dictee.service.TextToSpeech;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.text.Normalizer;

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

        String outputFolder = applicationProperties.getAudio().getFolder();
        String fileName = stripAccents(word) + ".mp3";

        String[] cmdarray = {applicationProperties.getPython().getName()
            , script
            , "-o", outputFolder
            , "-f", fileName
            , "-w", word};
        log.info(StringUtils.join(cmdarray, " "));

        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(cmdarray);

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
            return fileName;
        }
        return null;
    }

    public static String stripAccents(String word) {
        String s = Normalizer.normalize(word, Normalizer.Form.NFD);
        s = s.replaceAll(" ","_");
        s = s.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]","");
        return s;
    }
}
