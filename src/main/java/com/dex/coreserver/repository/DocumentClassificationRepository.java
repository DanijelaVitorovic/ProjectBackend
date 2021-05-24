package com.dex.coreserver.repository;

import com.dex.coreserver.model.DocumentClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentClassificationRepository extends JpaRepository<DocumentClassification, Long> {
}
