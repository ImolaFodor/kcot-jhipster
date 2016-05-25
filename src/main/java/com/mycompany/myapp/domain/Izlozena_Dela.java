package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Izlozena_dela.
 */
@Entity
@Table(name = "izlozena_dela")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Izlozena_Dela implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "naziv")
    private String naziv;

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
        Izlozena_Dela izlozena_dela = (Izlozena_Dela) o;
        if(izlozena_dela.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, izlozena_dela.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Izlozena_dela{" +
            "id=" + id +
            ", naziv='" + naziv + "'" +
            ", opis='" + opis + "'" +
            '}';
    }
}
