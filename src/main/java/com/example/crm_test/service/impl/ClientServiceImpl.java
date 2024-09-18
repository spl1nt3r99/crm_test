package com.example.crm_test.service.impl;

import com.example.crm_test.dto.ClientDto;
import com.example.crm_test.dto.ClientRequestDto;
import com.example.crm_test.mapper.ClientMapper;
import com.example.crm_test.model.Client;
import com.example.crm_test.repository.ClientRepository;
import com.example.crm_test.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.crm_test.repository.ClientRepository.Specs.byDomainLike;
import static com.example.crm_test.repository.ClientRepository.Specs.byNameLike;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto getClientById(Integer id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No client found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientDto> getAllClients(String name, String domain) {
        Specification<Client> specification = Specification
                .where(byNameLike(name))
                .and(byDomainLike(domain));

        var clients = clientRepository.findAll(specification);
        return clientMapper.toList(clients);
    }

    @Override
    public ClientDto saveClient(ClientRequestDto clientDto) {
        var client = clientMapper.toEntity(clientDto);
        var saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }

    @Override
    @CacheEvict(value = "employees", allEntries = true)
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}
