package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * A USali.
 */
@Entity
@Table(name = "u_sali")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class USali implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "naziv", nullable = false)
    private String naziv;

    @NotNull
    @Column(name = "poslovna_godina", nullable = false)
    private Integer poslovna_godina;

    @Column(name = "kontakt_ime")
    private String kontakt_ime;

    @Column(name = "kontakt_prz")
    private String kontakt_prz;

    @Column(name = "kontakt_broj")
    private Integer kontakt_broj;

    @Column(name = "kontakt_email")
    private String kontakt_email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "zarada")
    private Integer zarada;

    @Column(name = "prihod")
    private Integer prihod;

    @Column(name = "procenat")
    private Integer procenat;

    @Column(name = "posecenost")
    private Integer posecenost;

    @Column(name = "titl")
    private Boolean titl;

    @Column(name = "oprema")
    private Boolean oprema;

    @Column(name = "napomene")
    private Long napomene;

    @Column(name = "datum")
    private ZonedDateTime datum;

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

    public Integer getPoslovna_godina() {
        return poslovna_godina;
    }

    public void setPoslovna_godina(Integer poslovna_godina) {
        this.poslovna_godina = poslovna_godina;
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

    public Integer getKontakt_broj() {
        return kontakt_broj;
    }

    public void setKontakt_broj(Integer kontakt_broj) {
        this.kontakt_broj = kontakt_broj;
    }

    public String getKontakt_email() {
        return kontakt_email;
    }

    public void setKontakt_email(String kontakt_email) {
        this.kontakt_email = kontakt_email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getZarada() {
        return zarada;
    }

    public void setZarada(Integer zarada) {
        this.zarada = zarada;
    }

    public Integer getPrihod() {
        return prihod;
    }

    public void setPrihod(Integer prihod) {
        this.prihod = prihod;
    }

    public Integer getProcenat() {
        return procenat;
    }

    public void setProcenat(Integer procenat) {
        this.procenat = procenat;
    }

    public Integer getPosecenost() {
        return posecenost;
    }

    public void setPosecenost(Integer posecenost) {
        this.posecenost = posecenost;
    }

    public Boolean isTitl() {
        return titl;
    }

    public void setTitl(Boolean titl) {
        this.titl = titl;
    }

    public Boolean isOprema() {
        return oprema;
    }

    public void setOprema(Boolean oprema) {
        this.oprema = oprema;
    }

    public Long getNapomene() {
        return napomene;
    }

    public void setNapomene(Long napomene) {
        this.napomene = napomene;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        USali uSali = (USali) o;
        if(uSali.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, uSali.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "USali{" +
            "id=" + id +
            ", naziv='" + naziv + "'" +
            ", poslovna_godina='" + poslovna_godina + "'" +
            ", kontakt_ime='" + kontakt_ime + "'" +
            ", kontakt_prz='" + kontakt_prz + "'" +
            ", kontakt_broj='" + kontakt_broj + "'" +
            ", kontakt_email='" + kontakt_email + "'" +
            ", status='" + status + "'" +
            ", zarada='" + zarada + "'" +
            ", prihod='" + prihod + "'" +
            ", procenat='" + procenat + "'" +
            ", posecenost='" + posecenost + "'" +
            ", titl='" + titl + "'" +
            ", oprema='" + oprema + "'" +
            ", napomene='" + napomene + "'" +
            ", datum='" + datum + "'" +
            '}';
    }
}
