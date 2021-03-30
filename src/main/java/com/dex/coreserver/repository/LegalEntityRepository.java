package com.dex.coreserver.repository;

import com.dex.coreserver.model.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {

}
