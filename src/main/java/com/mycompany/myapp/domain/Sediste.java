package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sediste.
 */
@Entity
@Table(name = "sediste")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sediste implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "red", nullable = false)
    private Integer red;

    @NotNull
    @Column(name = "broj", nullable = false)
    private Integer broj;

    @OneToMany(mappedBy = "sediste")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RezervisanoSediste> sedistes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getBroj() {
        return broj;
    }

    public void setBroj(Integer broj) {
        this.broj = broj;
    }

    public Set<RezervisanoSediste> getSedistes() {
        return sedistes;
    }

    public void setSedistes(Set<RezervisanoSediste> rezervisanoSedistes) {
        this.sedistes = rezervisanoSedistes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sediste sediste = (Sediste) o;
        if(sediste.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sediste.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sediste{" +
            "id=" + id +
            ", red='" + red + "'" +
            ", broj='" + broj + "'" +
            '}';
    }
}
