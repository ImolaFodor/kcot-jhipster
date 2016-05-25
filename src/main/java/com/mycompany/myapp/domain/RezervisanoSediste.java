package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RezervisanoSediste.
 */
@Entity
@Table(name = "rezervisano_sediste")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RezervisanoSediste implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "opis")
    private Long opis;

    @ManyToOne
    @NotNull
    private Sediste sediste;

    @ManyToOne
    @NotNull
    private RezervacijaProdaja rezervacijaProdaja;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpis() {
        return opis;
    }

    public void setOpis(Long opis) {
        this.opis = opis;
    }

    public Sediste getSediste() {
        return sediste;
    }

    public void setSediste(Sediste sediste) {
        this.sediste = sediste;
    }

    public RezervacijaProdaja getRezervacijaProdaja() {
        return rezervacijaProdaja;
    }

    public void setRezervacijaProdaja(RezervacijaProdaja rezervacijaProdaja) {
        this.rezervacijaProdaja = rezervacijaProdaja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RezervisanoSediste rezervisanoSediste = (RezervisanoSediste) o;
        if(rezervisanoSediste.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rezervisanoSediste.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RezervisanoSediste{" +
            "id=" + id +
            ", opis='" + opis + "'" +
            '}';
    }
}
