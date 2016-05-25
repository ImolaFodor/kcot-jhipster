package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.USali;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the USali entity.
 */
@SuppressWarnings("unused")
public interface USaliRepository extends JpaRepository<USali,Long> {

}
