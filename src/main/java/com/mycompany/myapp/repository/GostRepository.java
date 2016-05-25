package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Gost;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gost entity.
 */
@SuppressWarnings("unused")
public interface GostRepository extends JpaRepository<Gost,Long> {

}
