package com.example.crm_test.mapper;

import com.example.crm_test.dto.ClientDto;
import com.example.crm_test.dto.ClientRequestDto;
import com.example.crm_test.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientDto toDto(Client entity);

    Client toEntity(ClientRequestDto dto);

    List<ClientDto> toList(Collection<Client> entities);
}
