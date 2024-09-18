package com.example.crm_test.service;

import com.example.crm_test.dto.ClientDto;
import com.example.crm_test.dto.ClientRequestDto;

import java.util.List;

public interface ClientService {

    ClientDto getClientById(Integer id);

    List<ClientDto> getAllClients(String name, String domain);

    ClientDto saveClient(ClientRequestDto clientDto);

    void deleteClient(Integer id);
}
