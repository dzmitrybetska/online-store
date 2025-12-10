package com.upgrade.store.domain.model.profile;

import com.upgrade.store.domain.model.Account;
import com.upgrade.store.domain.model.common.DataEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manager_profiles")
public class ManagerProfile extends DataEntity {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private Account account;
}
