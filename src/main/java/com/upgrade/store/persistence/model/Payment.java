package com.upgrade.store.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true, exclude = "order")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "payments")
public abstract class Payment extends DataEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "time_of_creation", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "confirmation_time")
    private LocalDateTime confirmedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public abstract PaymentMethod getMethod();
}
