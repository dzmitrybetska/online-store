package com.upgrade.store.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory extends DataEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", nullable = false)
    private OrderStatus name;

    @Column(name = "status_change_time", nullable = false, updatable = false)
    private LocalDateTime statusChangedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User changedBy;
}
