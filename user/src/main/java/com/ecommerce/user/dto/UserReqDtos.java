package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserReqDtos {
    private String username;

    private String email;

    private String name;
    private String password;
    private String role;
    private AddressReqDto address;

}
