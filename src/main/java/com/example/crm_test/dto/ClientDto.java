package com.example.crm_test.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClientDto implements Serializable {

    private int id;
    private String name;
    private String domain;
    private String address;
}
