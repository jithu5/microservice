package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class AddressResDto {

    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
