package com.example.crm_test.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDto implements Serializable {

    private String description;
    private LocalDate executionPeriod;
}
