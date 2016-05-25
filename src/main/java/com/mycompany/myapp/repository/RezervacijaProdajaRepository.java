package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RezervacijaProdaja;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RezervacijaProdaja entity.
 */
@SuppressWarnings("unused")
public interface RezervacijaProdajaRepository extends JpaRepository<RezervacijaProdaja,Long> {

    @Query("select distinct rezervacijaProdaja from RezervacijaProdaja rezervacijaProdaja left join fetch rezervacijaProdaja.gosts")
    List<RezervacijaProdaja> findAllWithEagerRelationships();

    @Query("select rezervacijaProdaja from RezervacijaProdaja rezervacijaProdaja left join fetch rezervacijaProdaja.gosts where rezervacijaProdaja.id =:id")
    RezervacijaProdaja findOneWithEagerRelationships(@Param("id") Long id);

}
