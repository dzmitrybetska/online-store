package com.upgrade.store.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "with")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DeliveryAddress {

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
