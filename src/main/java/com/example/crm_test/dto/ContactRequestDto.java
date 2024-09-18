package com.example.crm_test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private int clientId;
}
