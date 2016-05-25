package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Status;

/**
 * A Pozvani.
 */
@Entity
@Table(name = "pozvani")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pozvani implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @NotNull
    private UGaleriji uGaleriji;

    @ManyToOne
    @NotNull
    private Gost gost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UGaleriji getUGaleriji() {
        return uGaleriji;
    }

    public void setUGaleriji(UGaleriji uGaleriji) {
        this.uGaleriji = uGaleriji;
    }

    public Gost getGost() {
        return gost;
    }

    public void setGost(Gost gost) {
        this.gost = gost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pozvani pozvani = (Pozvani) o;
        if(pozvani.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pozvani.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pozvani{" +
            "id=" + id +
            ", status='" + status + "'" +
            '}';
    }
}
