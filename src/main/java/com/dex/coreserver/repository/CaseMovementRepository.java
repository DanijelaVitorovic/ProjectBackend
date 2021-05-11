package com.dex.coreserver.repository;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.CaseMovement;
import com.dex.coreserver.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaseMovementRepository extends JpaRepository<CaseMovement, Long> {

    @Query("from CaseMovement where (employeeOwner = :employee or employeeProcessor = :employee) and movementState = :sent")
    List<CaseMovement> findCaseMovementByOwnerOrProcessorIdAndStateSent(@Param("employee") Employee employee,@Param("sent") CaseMovement.MovementState sent);

    List<CaseMovement> findBy_case(Case caseForUpdate);
}
