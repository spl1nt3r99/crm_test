package com.example.crm_test.mapper;

import com.example.crm_test.dto.ContactDto;
import com.example.crm_test.dto.ContactRequestDto;
import com.example.crm_test.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ClientMapper.class})
public interface ContactMapper {

    ContactDto toDto(Contact entity);

    @Mapping(target = "client", ignore = true)
    Contact toEntity(ContactRequestDto dto);

    List<ContactDto> toList(Collection<Contact> entities);
}
