package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Skola.
 */
@Entity
@Table(name = "skola")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skola implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "kontakt_ime")
    private String kontakt_ime;

    @Column(name = "kontakt_prz")
    private String kontakt_prz;

    @Column(name = "kontakt_broj")
    private String kontakt_broj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKontakt_ime() {
        return kontakt_ime;
    }

    public void setKontakt_ime(String kontakt_ime) {
        this.kontakt_ime = kontakt_ime;
    }

    public String getKontakt_prz() {
        return kontakt_prz;
    }

    public void setKontakt_prz(String kontakt_prz) {
        this.kontakt_prz = kontakt_prz;
    }

    public String getKontakt_broj() {
        return kontakt_broj;
    }

    public void setKontakt_broj(String kontakt_broj) {
        this.kontakt_broj = kontakt_broj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Skola skola = (Skola) o;
        if(skola.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, skola.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Skola{" +
            "id=" + id +
            ", naziv='" + naziv + "'" +
            ", kontakt_ime='" + kontakt_ime + "'" +
            ", kontakt_prz='" + kontakt_prz + "'" +
            ", kontakt_broj='" + kontakt_broj + "'" +
            '}';
    }
}
