package com.modo.apps.dictee.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Table dictee for a new set of words
 */
@ApiModel(description = "Table dictee for a new set of words")
@Entity
@Table(name = "dictee")
public class Dictee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dicteedate")
    private LocalDate dicteedate;

    @ManyToMany
    @JoinTable(name = "dictee_word",
               joinColumns = @JoinColumn(name="dictees_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="words_id", referencedColumnName="id"))
    private Set<Question> words = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Dictee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDicteedate() {
        return dicteedate;
    }

    public Dictee dicteedate(LocalDate dicteedate) {
        this.dicteedate = dicteedate;
        return this;
    }

    public void setDicteedate(LocalDate dicteedate) {
        this.dicteedate = dicteedate;
    }

    public Set<Question> getWords() {
        return words;
    }

    public Dictee words(Set<Question> questions) {
        this.words = questions;
        return this;
    }

    public Dictee addWord(Question question) {
        this.words.add(question);
        question.getDicteeids().add(this);
        return this;
    }

    public Dictee removeWord(Question question) {
        this.words.remove(question);
        question.getDicteeids().remove(this);
        return this;
    }

    public void setWords(Set<Question> questions) {
        this.words = questions;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dictee dictee = (Dictee) o;
        if (dictee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dictee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dictee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dicteedate='" + getDicteedate() + "'" +
            "}";
    }
}
