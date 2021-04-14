package com.dex.coreserver.repository;

import com.dex.coreserver.model.CaseClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseClassificationRepository extends JpaRepository<CaseClassification, Long> {
}
