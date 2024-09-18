package com.example.crm_test.mapper;

import com.example.crm_test.dto.TaskDto;
import com.example.crm_test.dto.TaskRequestDto;
import com.example.crm_test.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ContactMapper.class})
public interface TaskMapper {

    TaskDto toDto(Task entity);

    @Mapping(target = "contact", ignore = true)
    Task toEntity(TaskRequestDto dto);

    List<TaskDto> toList(Collection<Task> entities);
}
