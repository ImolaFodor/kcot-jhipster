package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sediste;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sediste entity.
 */
@SuppressWarnings("unused")
public interface SedisteRepository extends JpaRepository<Sediste,Long> {

}
