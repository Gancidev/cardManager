package com.gancidev.cardmanager.dto.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
}