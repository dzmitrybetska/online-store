package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
