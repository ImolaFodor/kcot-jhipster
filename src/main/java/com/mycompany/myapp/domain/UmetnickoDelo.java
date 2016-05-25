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

import com.mycompany.myapp.domain.enumeration.TipUmDela;

/**
 * A UmetnickoDelo.
 */
@Entity
@Table(name = "umetnicko_delo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UmetnickoDelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "umetnik_ime")
    private String umetnik_ime;

    @Column(name = "umetnik_prz")
    private String umetnik_prz;

    @Column(name = "inventarski_broj")
    private String inventarski_broj;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tip_um_dela", nullable = false)
    private TipUmDela tip_um_dela;

    @OneToMany(mappedBy = "umetnickoDelo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Donirana_dela> donirana_delas = new HashSet<>();

    @OneToMany(mappedBy = "umetnickoDelo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Izlozena_Dela> izlozena_delas = new HashSet<>();

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

    public String getUmetnik_ime() {
        return umetnik_ime;
    }

    public void setUmetnik_ime(String umetnik_ime) {
        this.umetnik_ime = umetnik_ime;
    }

    public String getUmetnik_prz() {
        return umetnik_prz;
    }

    public void setUmetnik_prz(String umetnik_prz) {
        this.umetnik_prz = umetnik_prz;
    }

    public String getInventarski_broj() {
        return inventarski_broj;
    }

    public void setInventarski_broj(String inventarski_broj) {
        this.inventarski_broj = inventarski_broj;
    }

    public TipUmDela getTip_um_dela() {
        return tip_um_dela;
    }

    public void setTip_um_dela(TipUmDela tip_um_dela) {
        this.tip_um_dela = tip_um_dela;
    }

    public Set<Donirana_dela> getDonirana_delas() {
        return donirana_delas;
    }

    public void setDonirana_delas(Set<Donirana_dela> donirana_delas) {
        this.donirana_delas = donirana_delas;
    }

    public Set<Izlozena_Dela> getIzlozena_delas() {
        return izlozena_delas;
    }

    public void setIzlozena_delas(Set<Izlozena_Dela> izlozena_delas) {
        this.izlozena_delas = izlozena_delas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UmetnickoDelo umetnickoDelo = (UmetnickoDelo) o;
        if(umetnickoDelo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, umetnickoDelo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UmetnickoDelo{" +
            "id=" + id +
            ", naziv='" + naziv + "'" +
            ", umetnik_ime='" + umetnik_ime + "'" +
            ", umetnik_prz='" + umetnik_prz + "'" +
            ", inventarski_broj='" + inventarski_broj + "'" +
            ", tip_um_dela='" + tip_um_dela + "'" +
            '}';
    }
}
