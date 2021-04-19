package com.dex.coreserver.repository;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findBy_case(Case newCase);
}
