package com.upgrade.store.domain.model.profile;

import com.upgrade.store.domain.model.Account;
import com.upgrade.store.domain.model.DeliveryAddress;
import com.upgrade.store.domain.model.Order;
import com.upgrade.store.domain.model.common.DataEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"userAccount", "addresses", "orders"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_profiles")
public class CustomerProfile extends DataEntity {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private List<DeliveryAddress> addresses = new ArrayList<>(3);

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;
}
