package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UmetnickoDelo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UmetnickoDelo entity.
 */
@SuppressWarnings("unused")
public interface UmetnickoDeloRepository extends JpaRepository<UmetnickoDelo,Long> {

}
