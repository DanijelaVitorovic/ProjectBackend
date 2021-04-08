package com.dex.coreserver.service;

import com.dex.coreserver.model.Case;

import java.util.List;

public interface CaseService extends BasicService<Case>{
    public List<Case> deleteByIdAndReturnFindAll(Long id, String username);

}
