package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UGaleriji;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UGaleriji entity.
 */
@SuppressWarnings("unused")
public interface UGalerijiRepository extends JpaRepository<UGaleriji,Long> {

}
