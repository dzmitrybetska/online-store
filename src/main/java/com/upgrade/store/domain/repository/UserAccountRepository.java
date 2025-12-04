package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
