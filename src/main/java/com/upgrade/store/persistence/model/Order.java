package com.upgrade.store.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"account", "items", "histories", "payments"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends DataEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private UserAccount account;

    @Column(name = "orderDate", nullable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderItem> items = new HashSet<>(20);

    @Embedded
    private DeliveryAddress address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Set<OrderStatusHistory> histories = new HashSet<>();

    @OneToMany(mappedBy = "order", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Payment> payments;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
    }
}
