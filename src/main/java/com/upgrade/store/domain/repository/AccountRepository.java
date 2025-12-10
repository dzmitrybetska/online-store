package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUserId(Long userId);
}
