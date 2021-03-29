package com.dex.coreserver.repository;

import com.dex.coreserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User getById(Long id);
    List<User> findByIsDeletedOrderByIdAsc(boolean deleted);
}
