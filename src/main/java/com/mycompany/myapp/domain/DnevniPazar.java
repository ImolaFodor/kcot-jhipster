package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DnevniPazar.
 */
@Entity
@Table(name = "dnevni_pazar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DnevniPazar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_prodatog")
    private Integer id_prodatog;

    @Column(name = "iznos")
    private Integer iznos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getId_prodatog() {
        return id_prodatog;
    }

    public void setId_prodatog(Integer id_prodatog) {
        this.id_prodatog = id_prodatog;
    }

    public Integer getIznos() {
        return iznos;
    }

    public void setIznos(Integer iznos) {
        this.iznos = iznos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DnevniPazar dnevniPazar = (DnevniPazar) o;
        if(dnevniPazar.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dnevniPazar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DnevniPazar{" +
            "id=" + id +
            ", id_prodatog='" + id_prodatog + "'" +
            ", iznos='" + iznos + "'" +
            '}';
    }
}
