package com.portfolio.personal.repository;

import com.portfolio.personal.domain.Certification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findAllByOrderBySortOrderAscIdAsc();
}
