package com.example.crm_test.controller;

import com.example.crm_test.dto.ClientDto;
import com.example.crm_test.dto.ClientRequestDto;
import com.example.crm_test.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(@RequestParam(required = false) String name,
            @RequestParam(required = false) String domain) {
        return ResponseEntity.ok(clientService.getAllClients(name, domain));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.saveClient(clientRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
    }
}
