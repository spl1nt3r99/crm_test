package com.example.crm_test.dto;

import com.example.crm_test.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TaskDto implements Serializable {

    private int id;
    private String description;
    private TaskStatus status;
    private String executionPeriod;
    private ContactDto contact;
}
