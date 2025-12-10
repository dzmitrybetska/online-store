package com.upgrade.store.domain.model;

import com.upgrade.store.domain.model.common.DataEntity;
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
@Table(name = "delivery_addresses")
public class DeliveryAddress extends DataEntity {

    @Column(name = "zipcode", length = 6, nullable = false)
    private String zipcode;

    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Column(name = "house_number", length = 7, nullable = false)
    private String houseNumber;

    @Column(name = "flat_number", length = 5)
    private String flatNumber;
}
