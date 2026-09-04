package com.portfolio.personal.repository;

import com.portfolio.personal.domain.ServiceOffering;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    List<ServiceOffering> findByActiveTrueOrderBySortOrderAscIdAsc();

    List<ServiceOffering> findAllByOrderBySortOrderAscIdAsc();
}
