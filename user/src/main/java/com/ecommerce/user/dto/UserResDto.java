package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserResDto {

    private String id;
    private String username;
    private String email;
    private String name;
    private AddressResDto address;
}
