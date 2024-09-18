package com.example.crm_test.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ContactDto implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private ClientDto client;
}
