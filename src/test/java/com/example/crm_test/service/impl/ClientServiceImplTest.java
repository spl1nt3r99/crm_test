package com.example.crm_test.service.impl;

import com.example.crm_test.dto.ClientDto;
import com.example.crm_test.dto.ClientRequestDto;
import com.example.crm_test.mapper.ClientMapper;
import com.example.crm_test.model.Client;
import com.example.crm_test.repository.ClientRepository;
import com.example.crm_test.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {ClientServiceImpl.class})
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Test
    void test_getClientById() {
        var id = 1;
        var client = mock(Client.class);
        var clientDto = mock(ClientDto.class);

        given(clientRepository.findById(id)).willReturn(Optional.of(client));
        given(clientMapper.toDto(client)).willReturn(clientDto);

        var returned = clientService.getClientById(id);

        verify(clientRepository).findById(id);
        assertEquals(clientDto, returned);
    }

    @Test
    void test_getClientById_NotFound() {
        var id = 1;

        given(clientRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientService.getClientById(id));
    }

    @Test
    void test_getAllClients() {
        var name = "name";
        var domain = "domain";
        List<Client> clients = mock(List.class);
        List<ClientDto> clientDtos = mock(List.class);

        given(clientRepository.findAll(argThat((Specification<Client> spec) -> true))).willReturn(clients);
        given(clientMapper.toList(clients)).willReturn(clientDtos);

        var returned = clientService.getAllClients(name, domain);

        verify(clientRepository).findAll((Specification<Client>) ArgumentMatchers.any());
        assertEquals(clientDtos, returned);
    }

    @Test
    void test_saveClient() {
        var clientRequestDto = mock(ClientRequestDto.class);
        var client = mock(Client.class);
        var savedClient = mock(Client.class);
        var clientDto = mock(ClientDto.class);

        given(clientMapper.toEntity(clientRequestDto)).willReturn(client);
        given(clientRepository.save(client)).willReturn(savedClient);
        given(clientMapper.toDto(savedClient)).willReturn(clientDto);

        var returned = clientService.saveClient(clientRequestDto);

        verify(clientMapper).toEntity(clientRequestDto);
        verify(clientRepository).save(client);
        verify(clientMapper).toDto(savedClient);
        assertEquals(clientDto, returned);
    }

    @Test
    void test_deleteClient() {
        var id = 1;

        clientService.deleteClient(id);

        verify(clientRepository).deleteById(id);
    }
}