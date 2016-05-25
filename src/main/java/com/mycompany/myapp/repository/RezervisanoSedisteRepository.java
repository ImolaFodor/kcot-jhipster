package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RezervisanoSediste;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RezervisanoSediste entity.
 */
@SuppressWarnings("unused")
public interface RezervisanoSedisteRepository extends JpaRepository<RezervisanoSediste,Long> {

}
