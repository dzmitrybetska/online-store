package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.Role;
import com.upgrade.store.domain.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
