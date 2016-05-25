package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Donirana_dela.
 */
@Entity
@Table(name = "donirana_dela")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Donirana_dela implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "br_ugovora")
    private String br_ugovora;

    @Column(name = "opis")
    private Long opis;

    @ManyToOne
    @NotNull
    private UmetnickoDelo umetnickoDelo;

    @ManyToOne
    @NotNull
    private UGaleriji uGaleriji;

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

    public String getBr_ugovora() {
        return br_ugovora;
    }

    public void setBr_ugovora(String br_ugovora) {
        this.br_ugovora = br_ugovora;
    }

    public Long getOpis() {
        return opis;
    }

    public void setOpis(Long opis) {
        this.opis = opis;
    }

    public UmetnickoDelo getUmetnickoDelo() {
        return umetnickoDelo;
    }

    public void setUmetnickoDelo(UmetnickoDelo umetnickoDelo) {
        this.umetnickoDelo = umetnickoDelo;
    }

    public UGaleriji getUGaleriji() {
        return uGaleriji;
    }

    public void setUGaleriji(UGaleriji uGaleriji) {
        this.uGaleriji = uGaleriji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Donirana_dela donirana_dela = (Donirana_dela) o;
        if(donirana_dela.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, donirana_dela.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Donirana_dela{" +
            "id=" + id +
            ", naziv='" + naziv + "'" +
            ", br_ugovora='" + br_ugovora + "'" +
            ", opis='" + opis + "'" +
            '}';
    }
}
