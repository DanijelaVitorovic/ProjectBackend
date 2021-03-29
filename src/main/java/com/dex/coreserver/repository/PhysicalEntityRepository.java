package com.dex.coreserver.repository;

import com.dex.coreserver.model.PhysicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalEntityRepository extends JpaRepository<PhysicalEntity,Long> {

}
