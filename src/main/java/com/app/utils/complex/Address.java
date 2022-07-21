package com.app.utils.complex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private Integer zipcode;
    private Integer streetNumber;
    private String street;
    private String city;
    private String country;
}
