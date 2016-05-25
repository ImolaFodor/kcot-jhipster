package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Izlozena_Dela;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Izlozena_dela entity.
 */
@SuppressWarnings("unused")
public interface Izlozena_DelaRepository extends JpaRepository<Izlozena_Dela,Long> {

}
