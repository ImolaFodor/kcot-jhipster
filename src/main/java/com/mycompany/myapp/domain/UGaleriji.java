package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Tip;

import com.mycompany.myapp.domain.enumeration.StatusGal;

/**
 * A UGaleriji.
 */
@Entity
@Table(name = "u_galeriji")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UGaleriji implements Serializable {

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

    @Column(name = "moderator_ime")
    private String moderator_ime;

    @Column(name = "moderator_prz")
    private String moderator_prz;

    @Column(name = "moderator_broj")
    private String moderator_broj;

    @Column(name = "trosak")
    private Integer trosak;

    @Column(name = "unajmio_ime")
    private String unajmio_ime;

    @Column(name = "unajmio_prz")
    private String unajmio_prz;

    @Column(name = "unajmio_email")
    private String unajmio_email;

    @Column(name = "br_fakture")
    private String br_fakture;

    @Column(name = "zarada")
    private Integer zarada;

    @Column(name = "posecenost")
    private Integer posecenost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private Tip tip;

    @Column(name = "napomene")
    private Long napomene;

    @Column(name = "datum")
    private ZonedDateTime datum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusGal status;

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

    public String getModerator_ime() {
        return moderator_ime;
    }

    public void setModerator_ime(String moderator_ime) {
        this.moderator_ime = moderator_ime;
    }

    public String getModerator_prz() {
        return moderator_prz;
    }

    public void setModerator_prz(String moderator_prz) {
        this.moderator_prz = moderator_prz;
    }

    public String getModerator_broj() {
        return moderator_broj;
    }

    public void setModerator_broj(String moderator_broj) {
        this.moderator_broj = moderator_broj;
    }

    public Integer getTrosak() {
        return trosak;
    }

    public void setTrosak(Integer trosak) {
        this.trosak = trosak;
    }

    public String getUnajmio_ime() {
        return unajmio_ime;
    }

    public void setUnajmio_ime(String unajmio_ime) {
        this.unajmio_ime = unajmio_ime;
    }

    public String getUnajmio_prz() {
        return unajmio_prz;
    }

    public void setUnajmio_prz(String unajmio_prz) {
        this.unajmio_prz = unajmio_prz;
    }

    public String getUnajmio_email() {
        return unajmio_email;
    }

    public void setUnajmio_email(String unajmio_email) {
        this.unajmio_email = unajmio_email;
    }

    public String getBr_fakture() {
        return br_fakture;
    }

    public void setBr_fakture(String br_fakture) {
        this.br_fakture = br_fakture;
    }

    public Integer getZarada() {
        return zarada;
    }

    public void setZarada(Integer zarada) {
        this.zarada = zarada;
    }

    public Integer getPosecenost() {
        return posecenost;
    }

    public void setPosecenost(Integer posecenost) {
        this.posecenost = posecenost;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
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

    public StatusGal getStatus() {
        return status;
    }

    public void setStatus(StatusGal status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UGaleriji uGaleriji = (UGaleriji) o;
        if(uGaleriji.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, uGaleriji.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UGaleriji{" +
            "id=" + id +
            ", naziv='" + naziv + "'" +
            ", poslovna_godina='" + poslovna_godina + "'" +
            ", kontakt_ime='" + kontakt_ime + "'" +
            ", kontakt_prz='" + kontakt_prz + "'" +
            ", kontakt_broj='" + kontakt_broj + "'" +
            ", kontakt_email='" + kontakt_email + "'" +
            ", moderator_ime='" + moderator_ime + "'" +
            ", moderator_prz='" + moderator_prz + "'" +
            ", moderator_broj='" + moderator_broj + "'" +
            ", trosak='" + trosak + "'" +
            ", unajmio_ime='" + unajmio_ime + "'" +
            ", unajmio_prz='" + unajmio_prz + "'" +
            ", unajmio_email='" + unajmio_email + "'" +
            ", br_fakture='" + br_fakture + "'" +
            ", zarada='" + zarada + "'" +
            ", posecenost='" + posecenost + "'" +
            ", tip='" + tip + "'" +
            ", napomene='" + napomene + "'" +
            ", datum='" + datum + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
