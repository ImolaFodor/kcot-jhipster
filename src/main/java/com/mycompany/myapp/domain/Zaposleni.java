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

import com.mycompany.myapp.domain.enumeration.Tip;

/**
 * A Zaposleni.
 */
@Entity
@Table(name = "zaposleni")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zaposleni implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ime", nullable = false)
    private String ime;

    @NotNull
    @Column(name = "prz", nullable = false)
    private String prz;

    @Column(name = "korisnicko_ime")
    private String korisnicko_ime;

    @Column(name = "lozinka")
    private String lozinka;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private Tip tip;

    @OneToMany(mappedBy = "obavio_rez")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RezervacijaProdaja> obavio_rezs = new HashSet<>();

    @OneToMany(mappedBy = "obavio_prodaju")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RezervacijaProdaja> obavio_prodajus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrz() {
        return prz;
    }

    public void setPrz(String prz) {
        this.prz = prz;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Set<RezervacijaProdaja> getObavio_rezs() {
        return obavio_rezs;
    }

    public void setObavio_rezs(Set<RezervacijaProdaja> rezervacijaProdajas) {
        this.obavio_rezs = rezervacijaProdajas;
    }

    public Set<RezervacijaProdaja> getObavio_prodajus() {
        return obavio_prodajus;
    }

    public void setObavio_prodajus(Set<RezervacijaProdaja> rezervacijaProdajas) {
        this.obavio_prodajus = rezervacijaProdajas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Zaposleni zaposleni = (Zaposleni) o;
        if(zaposleni.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, zaposleni.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Zaposleni{" +
            "id=" + id +
            ", ime='" + ime + "'" +
            ", prz='" + prz + "'" +
            ", korisnicko_ime='" + korisnicko_ime + "'" +
            ", lozinka='" + lozinka + "'" +
            ", tip='" + tip + "'" +
            '}';
    }
}
