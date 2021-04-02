package com.dex.coreserver.service;

import com.dex.coreserver.model.PhysicalEntity;

import java.util.List;

public interface PhysicalEntityService extends BasicService<PhysicalEntity>{
    public List<PhysicalEntity> deleteByIdAndReturnFindAll(Long id, String username);
}
