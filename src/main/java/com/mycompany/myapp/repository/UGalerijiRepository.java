package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UGaleriji;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the UGaleriji entity.
 */
@SuppressWarnings("unused")
public interface UGalerijiRepository extends JpaRepository<UGaleriji,Long> {
}
