package com.dex.coreserver.service;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.CaseMovement;

import javax.transaction.Transactional;
import java.util.List;

public interface CaseService extends BasicService<Case> {
    List<Case> deleteByIdAndReturnFindAll(Long id, String username);
    CaseMovement addOwner(CaseMovement newCaseMovement, String username) throws Exception;
    CaseMovement addProcessor(CaseMovement caseMovement, String username) throws Exception;

    @Transactional
    CaseMovement revokeCaseMovement(Case caseForUpdate, String username) throws Exception;
}
