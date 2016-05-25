package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pozvani;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pozvani entity.
 */
@SuppressWarnings("unused")
public interface PozvaniRepository extends JpaRepository<Pozvani,Long> {

}
