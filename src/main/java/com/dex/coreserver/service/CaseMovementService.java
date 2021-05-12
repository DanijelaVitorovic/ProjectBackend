package com.dex.coreserver.service;

import com.dex.coreserver.model.CaseMovement;

import java.util.List;

public interface CaseMovementService {
    CaseMovement acceptCase(Long id, String username);
    List<CaseMovement> getCaseMovementList(String username);

    List<CaseMovement> findByCaseId(Long id);
}
