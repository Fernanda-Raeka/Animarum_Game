package com.fernanda.backend.repository;

import com.fernanda.backend.model.AccountProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountProgressRepository extends JpaRepository<AccountProgress, Integer> {
    List<AccountProgress> findByAccount_AccountId(Integer accountId);
}