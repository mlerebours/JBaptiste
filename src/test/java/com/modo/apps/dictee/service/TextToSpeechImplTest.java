package com.modo.apps.dictee.service;

import com.modo.apps.dictee.JBaptisteApp;
import com.modo.apps.dictee.service.impl.TextToSpeechImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JBaptisteApp.class)
public class TextToSpeechImplTest {
    @Test
    public void stripAccents() throws Exception {
        String s1 = TextToSpeechImpl.stripAccents("leçon");
        System.out.println(s1);
        Assert.assertEquals("lecon", s1);

        String s2 = TextToSpeechImpl.stripAccents("école");
        System.out.println(s2);
        Assert.assertEquals("ecole", s2);

        String s3 = TextToSpeechImpl.stripAccents("fenêtre");
        System.out.println(s3);
        Assert.assertEquals("fenetre", s3);

        String s4 = TextToSpeechImpl.stripAccents("la balle");
        System.out.println(s4);
        Assert.assertEquals("la_balle", s4);
    }

}
