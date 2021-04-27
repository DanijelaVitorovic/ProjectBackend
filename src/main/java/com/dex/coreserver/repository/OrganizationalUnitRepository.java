package com.dex.coreserver.repository;

import com.dex.coreserver.model.OrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnit, Long> {

//    @Query("from OrganizationalUnit where code = :code")
//    List<OrganizationalUnit> getActiveOrganizationalUnit(@Param("code") String code);

    Boolean existsByCode(String code);
}
