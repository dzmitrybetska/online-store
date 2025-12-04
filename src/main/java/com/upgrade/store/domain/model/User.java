package com.upgrade.store.domain.model;

import com.upgrade.store.domain.model.common.DataEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true, exclude = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends DataEntity {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
