package com.modo.apps.dictee.web.rest;

import com.modo.apps.dictee.JBaptisteApp;

import com.modo.apps.dictee.domain.Dictee;
import com.modo.apps.dictee.repository.DicteeRepository;
import com.modo.apps.dictee.service.DicteeService;
import com.modo.apps.dictee.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DicteeResource REST controller.
 *
 * @see DicteeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JBaptisteApp.class)
public class DicteeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DICTEEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DICTEEDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DicteeRepository dicteeRepository;

    @Autowired
    private DicteeService dicteeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDicteeMockMvc;

    private Dictee dictee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DicteeResource dicteeResource = new DicteeResource(dicteeService);
        this.restDicteeMockMvc = MockMvcBuilders.standaloneSetup(dicteeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictee createEntity(EntityManager em) {
        Dictee dictee = new Dictee()
            .name(DEFAULT_NAME)
            .dicteedate(DEFAULT_DICTEEDATE);
        return dictee;
    }

    @Before
    public void initTest() {
        dictee = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictee() throws Exception {
        int databaseSizeBeforeCreate = dicteeRepository.findAll().size();

        // Create the Dictee
        restDicteeMockMvc.perform(post("/api/dictees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictee)))
            .andExpect(status().isCreated());

        // Validate the Dictee in the database
        List<Dictee> dicteeList = dicteeRepository.findAll();
        assertThat(dicteeList).hasSize(databaseSizeBeforeCreate + 1);
        Dictee testDictee = dicteeList.get(dicteeList.size() - 1);
        assertThat(testDictee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDictee.getDicteedate()).isEqualTo(DEFAULT_DICTEEDATE);
    }

    @Test
    @Transactional
    public void createDicteeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dicteeRepository.findAll().size();

        // Create the Dictee with an existing ID
        dictee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDicteeMockMvc.perform(post("/api/dictees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictee)))
            .andExpect(status().isBadRequest());

        // Validate the Dictee in the database
        List<Dictee> dicteeList = dicteeRepository.findAll();
        assertThat(dicteeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDictees() throws Exception {
        // Initialize the database
        dicteeRepository.saveAndFlush(dictee);

        // Get all the dicteeList
        restDicteeMockMvc.perform(get("/api/dictees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dicteedate").value(hasItem(DEFAULT_DICTEEDATE.toString())));
    }

    @Test
    @Transactional
    public void getDictee() throws Exception {
        // Initialize the database
        dicteeRepository.saveAndFlush(dictee);

        // Get the dictee
        restDicteeMockMvc.perform(get("/api/dictees/{id}", dictee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dictee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dicteedate").value(DEFAULT_DICTEEDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDictee() throws Exception {
        // Get the dictee
        restDicteeMockMvc.perform(get("/api/dictees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictee() throws Exception {
        // Initialize the database
        dicteeService.save(dictee);

        int databaseSizeBeforeUpdate = dicteeRepository.findAll().size();

        // Update the dictee
        Dictee updatedDictee = dicteeRepository.findOne(dictee.getId());
        updatedDictee
            .name(UPDATED_NAME)
            .dicteedate(UPDATED_DICTEEDATE);

        restDicteeMockMvc.perform(put("/api/dictees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDictee)))
            .andExpect(status().isOk());

        // Validate the Dictee in the database
        List<Dictee> dicteeList = dicteeRepository.findAll();
        assertThat(dicteeList).hasSize(databaseSizeBeforeUpdate);
        Dictee testDictee = dicteeList.get(dicteeList.size() - 1);
        assertThat(testDictee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDictee.getDicteedate()).isEqualTo(UPDATED_DICTEEDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDictee() throws Exception {
        int databaseSizeBeforeUpdate = dicteeRepository.findAll().size();

        // Create the Dictee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDicteeMockMvc.perform(put("/api/dictees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictee)))
            .andExpect(status().isCreated());

        // Validate the Dictee in the database
        List<Dictee> dicteeList = dicteeRepository.findAll();
        assertThat(dicteeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDictee() throws Exception {
        // Initialize the database
        dicteeService.save(dictee);

        int databaseSizeBeforeDelete = dicteeRepository.findAll().size();

        // Get the dictee
        restDicteeMockMvc.perform(delete("/api/dictees/{id}", dictee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dictee> dicteeList = dicteeRepository.findAll();
        assertThat(dicteeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dictee.class);
        Dictee dictee1 = new Dictee();
        dictee1.setId(1L);
        Dictee dictee2 = new Dictee();
        dictee2.setId(dictee1.getId());
        assertThat(dictee1).isEqualTo(dictee2);
        dictee2.setId(2L);
        assertThat(dictee1).isNotEqualTo(dictee2);
        dictee1.setId(null);
        assertThat(dictee1).isNotEqualTo(dictee2);
    }
}
