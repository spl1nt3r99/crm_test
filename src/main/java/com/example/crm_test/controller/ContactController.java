package com.example.crm_test.controller;

import com.example.crm_test.dto.ContactDto;
import com.example.crm_test.dto.ContactRequestDto;
import com.example.crm_test.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(@RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(contactService.getAllContacts(name, email));
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactRequestDto contactRequestDto) {
        return ResponseEntity.ok(contactService.saveContact(contactRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getClient(@PathVariable Integer id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Integer id) {
        contactService.deleteContact(id);
    }
}
