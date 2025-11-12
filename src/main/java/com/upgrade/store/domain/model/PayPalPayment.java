package com.upgrade.store.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paypal_payments")
public class PayPalPayment extends Payment {

    @Column(name = "paypal_account", nullable = false)
    private String paypalAccount;

    @Override
    public PaymentMethod getMethod() {
        return PaymentMethod.PAYPAL;
    }
}
