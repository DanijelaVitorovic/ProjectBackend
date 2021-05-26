package com.dex.coreserver.repository;

import com.dex.coreserver.model.CaseMovement;
import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentMovement;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.enums.DocumentMovementStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMovementRepository extends JpaRepository<DocumentMovement, Long> {


    @Query("from DocumentMovement " +
            "where (employeeCreated = :employee or verificationEmployee = :employee or " +
            "signingEmployee = :employee or signedEmployee = :employee or " +
            "finalEmployee = :employee)  and " +
            "documentMovementStatement = :sent")
    List<DocumentMovement> findEmployeeByUserAndStateSent(@Param("employee") Employee employee, @Param("sent") DocumentMovementStatement sent);

    @Query("from DocumentMovement " +
            "where document = :updateDocument " +
            "and documentMovementStatement = :sent")
    DocumentMovement findDocumentMovementByDocumentAndStateSent(@Param("updateDocument") Document updateDocument,@Param("sent") DocumentMovementStatement sent);

}
