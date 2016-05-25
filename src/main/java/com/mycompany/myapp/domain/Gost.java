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

import com.mycompany.myapp.domain.enumeration.TipGost;

/**
 * A Gost.
 */
@Entity
@Table(name = "gost")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gost implements Serializable {

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

    @Column(name = "broj")
    private Integer broj;

    @Column(name = "email")
    private String email;

    @Column(name = "adresa")
    private String adresa;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tip_gost", nullable = false)
    private TipGost tip_gost;

    @OneToMany(mappedBy = "gost")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pozvani> pozvanis = new HashSet<>();

    @ManyToMany(mappedBy = "gosts")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RezervacijaProdaja> rezervacijaProdajas = new HashSet<>();

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

    public Integer getBroj() {
        return broj;
    }

    public void setBroj(Integer broj) {
        this.broj = broj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public TipGost getTip_gost() {
        return tip_gost;
    }

    public void setTip_gost(TipGost tip_gost) {
        this.tip_gost = tip_gost;
    }

    public Set<Pozvani> getPozvanis() {
        return pozvanis;
    }

    public void setPozvanis(Set<Pozvani> pozvanis) {
        this.pozvanis = pozvanis;
    }

    public Set<RezervacijaProdaja> getRezervacijaProdajas() {
        return rezervacijaProdajas;
    }

    public void setRezervacijaProdajas(Set<RezervacijaProdaja> rezervacijaProdajas) {
        this.rezervacijaProdajas = rezervacijaProdajas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gost gost = (Gost) o;
        if(gost.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gost.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gost{" +
            "id=" + id +
            ", ime='" + ime + "'" +
            ", prz='" + prz + "'" +
            ", broj='" + broj + "'" +
            ", email='" + email + "'" +
            ", adresa='" + adresa + "'" +
            ", tip_gost='" + tip_gost + "'" +
            '}';
    }
}
