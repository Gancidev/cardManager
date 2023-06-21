package com.gancidev.cardmanager.dto.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
}