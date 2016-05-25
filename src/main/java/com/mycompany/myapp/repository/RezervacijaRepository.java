package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Rezervacija;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rezervacija entity.
 */
@SuppressWarnings("unused")
public interface RezervacijaRepository extends JpaRepository<Rezervacija,Long> {

}
