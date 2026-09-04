package com.portfolio.personal.repository;

import com.portfolio.personal.domain.Qualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findAllByOrderBySortOrderAscIdAsc();
}
