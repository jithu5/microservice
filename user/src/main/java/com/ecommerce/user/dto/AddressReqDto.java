package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class AddressReqDto {

    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

}
