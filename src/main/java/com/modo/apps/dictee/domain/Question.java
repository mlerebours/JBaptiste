package com.modo.apps.dictee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Wrods table and sound file
 */
@ApiModel(description = "Wrods table and sound file")
@Entity
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    @Column(name = "soundfile")
    private String soundfile;

    @ManyToMany(mappedBy = "words")
    @JsonIgnore
    private Set<Dictee> dicteeids = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public Question word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSoundfile() {
        return soundfile;
    }

    public Question soundfile(String soundfile) {
        this.soundfile = soundfile;
        return this;
    }

    public void setSoundfile(String soundfile) {
        this.soundfile = soundfile;
    }

    public Set<Dictee> getDicteeids() {
        return dicteeids;
    }

    public Question dicteeids(Set<Dictee> dictees) {
        this.dicteeids = dictees;
        return this;
    }

    public Question addDicteeid(Dictee dictee) {
        this.dicteeids.add(dictee);
        dictee.getWords().add(this);
        return this;
    }

    public Question removeDicteeid(Dictee dictee) {
        this.dicteeids.remove(dictee);
        dictee.getWords().remove(this);
        return this;
    }

    public void setDicteeids(Set<Dictee> dictees) {
        this.dicteeids = dictees;
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
        Question question = (Question) o;
        if (question.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", soundfile='" + getSoundfile() + "'" +
            "}";
    }
}
