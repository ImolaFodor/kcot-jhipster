package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Zaposleni;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Zaposleni entity.
 */
@SuppressWarnings("unused")
public interface ZaposleniRepository extends JpaRepository<Zaposleni,Long> {

}
