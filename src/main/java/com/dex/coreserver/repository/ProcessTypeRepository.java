package com.dex.coreserver.repository;

import com.dex.coreserver.model.ProcessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessTypeRepository extends JpaRepository<ProcessType, Long> {
}
