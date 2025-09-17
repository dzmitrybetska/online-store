package com.upgrade.store.persistence.model;

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
@Table(name = "blik_payments")
public class BlikPayment extends Payment {

    @Column(name = "blik_code", nullable = false, length = 6)
    private String blikCode;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Override
    public PaymentMethod getMethod() {
        return PaymentMethod.BLIK;
    }
}
