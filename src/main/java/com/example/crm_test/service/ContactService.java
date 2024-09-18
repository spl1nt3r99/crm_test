package com.example.crm_test.service;

import com.example.crm_test.dto.ContactDto;
import com.example.crm_test.dto.ContactRequestDto;

import java.util.List;

public interface ContactService {
    ContactDto getContactById(Integer id);

    List<ContactDto> getAllContacts(String name, String email);

    ContactDto saveContact(ContactRequestDto contactDto);

    void deleteContact(Integer id);
}
