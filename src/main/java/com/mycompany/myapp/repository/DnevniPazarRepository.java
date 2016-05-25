package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DnevniPazar;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DnevniPazar entity.
 */
@SuppressWarnings("unused")
public interface DnevniPazarRepository extends JpaRepository<DnevniPazar,Long> {

}
