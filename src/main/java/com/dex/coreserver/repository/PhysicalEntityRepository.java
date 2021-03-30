package com.dex.coreserver.repository;

import com.dex.coreserver.model.PhysicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalEntityRepository extends JpaRepository<PhysicalEntity,Long> {

}
