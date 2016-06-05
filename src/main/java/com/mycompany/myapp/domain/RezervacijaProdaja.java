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

import com.mycompany.myapp.domain.enumeration.StatusRezProd;

/**
 * A RezervacijaProdaja.
 */
@Entity
@Table(name = "rezervacija_prodaja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RezervacijaProdaja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rezervisao_ime")
    private String rezervisao_ime;

    @Column(name = "cena")
    private Integer cena;

    @Column(name = "broj_karata")
    private Integer broj_karata;

    @Column(name = "br_male_dece")
    private Integer br_male_dece;

    @Column(name = "br_velike_dece")
    private Integer br_velike_dece;

    @Column(name = "firma")
    private String firma;

    @Column(name = "aktivna_rez")
    private Boolean aktivna_rez;

    @Column(name = "opis")
    private Long opis;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_rez_prod", nullable = false)
    private StatusRezProd status_rez_prod;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rezervacija_prodaja_gost",
               joinColumns = @JoinColumn(name="rezervacija_prodajas_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="gosts_id", referencedColumnName="ID"))
    private Set<Gost> gosts = new HashSet<>();

    @ManyToOne
    private Skola skola;

    @ManyToOne
    private Zaposleni obavio_rez;

    @ManyToOne
    private Zaposleni obavio_prodaju;

    @ManyToOne
    @NotNull
    private USali dogadjaj;

    @OneToMany(mappedBy = "rezsed")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RezervisanoSediste> rezseds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRezervisao_ime() {
        return rezervisao_ime;
    }

    public void setRezervisao_ime(String rezervisao_ime) {
        this.rezervisao_ime = rezervisao_ime;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Integer getBroj_karata() {
        return broj_karata;
    }

    public void setBroj_karata(Integer broj_karata) {
        this.broj_karata = broj_karata;
    }

    public Integer getBr_male_dece() {
        return br_male_dece;
    }

    public void setBr_male_dece(Integer br_male_dece) {
        this.br_male_dece = br_male_dece;
    }

    public Integer getBr_velike_dece() {
        return br_velike_dece;
    }

    public void setBr_velike_dece(Integer br_velike_dece) {
        this.br_velike_dece = br_velike_dece;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Boolean isAktivna_rez() {
        return aktivna_rez;
    }

    public void setAktivna_rez(Boolean aktivna_rez) {
        this.aktivna_rez = aktivna_rez;
    }

    public Long getOpis() {
        return opis;
    }

    public void setOpis(Long opis) {
        this.opis = opis;
    }

    public StatusRezProd getStatus_rez_prod() {
        return status_rez_prod;
    }

    public void setStatus_rez_prod(StatusRezProd status_rez_prod) {
        this.status_rez_prod = status_rez_prod;
    }

    public Set<Gost> getGosts() {
        return gosts;
    }

    public void setGosts(Set<Gost> gosts) {
        this.gosts = gosts;
    }

    public Skola getSkola() {
        return skola;
    }

    public void setSkola(Skola skola) {
        this.skola = skola;
    }

    public Zaposleni getObavio_rez() {
        return obavio_rez;
    }

    public void setObavio_rez(Zaposleni zaposleni) {
        this.obavio_rez = zaposleni;
    }

    public Zaposleni getObavio_prodaju() {
        return obavio_prodaju;
    }

    public void setObavio_prodaju(Zaposleni zaposleni) {
        this.obavio_prodaju = zaposleni;
    }

    public USali getDogadjaj() {
        return dogadjaj;
    }

    public void setDogadjaj(USali uSali) {
        this.dogadjaj = uSali;
    }

    public Set<RezervisanoSediste> getRezseds() {
        return rezseds;
    }

    public void setRezseds(Set<RezervisanoSediste> rezervisanoSedistes) {
        this.rezseds = rezervisanoSedistes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RezervacijaProdaja rezervacijaProdaja = (RezervacijaProdaja) o;
        if(rezervacijaProdaja.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rezervacijaProdaja.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RezervacijaProdaja{" +
            "id=" + id +
            ", rezervisao_ime='" + rezervisao_ime + "'" +
            ", cena='" + cena + "'" +
            ", broj_karata='" + broj_karata + "'" +
            ", br_male_dece='" + br_male_dece + "'" +
            ", br_velike_dece='" + br_velike_dece + "'" +
            ", firma='" + firma + "'" +
            ", aktivna_rez='" + aktivna_rez + "'" +
            ", opis='" + opis + "'" +
            ", status_rez_prod='" + status_rez_prod + "'" +
            '}';
    }
}
