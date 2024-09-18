package com.example.crm_test.service.impl;

import com.example.crm_test.dto.ContactDto;
import com.example.crm_test.dto.ContactRequestDto;
import com.example.crm_test.exception.ElementAlreadyExistsException;
import com.example.crm_test.mapper.ContactMapper;
import com.example.crm_test.model.Client;
import com.example.crm_test.model.Contact;
import com.example.crm_test.repository.ClientRepository;
import com.example.crm_test.repository.ContactRepository;
import com.example.crm_test.service.ContactService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ContactServiceImpl.class})
class ContactServiceImplTest {

    @MockBean
    private ContactRepository contactRepository;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ContactMapper contactMapper;

    @Autowired
    private ContactService contactService;

    @Test
    void test_getContactById() {
        var id = 1;
        var contact = mock(Contact.class);
        var contactDto = mock(ContactDto.class);

        given(contactRepository.findById(id)).willReturn(Optional.of(contact));
        given(contactMapper.toDto(contact)).willReturn(contactDto);

        var returned = contactService.getContactById(id);

        verify(contactRepository).findById(id);
        assertEquals(contactDto, returned);
    }

    @Test
    void test_getContactById_NotFound() {
        var id = 1;

        given(contactRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> contactService.getContactById(id));
    }

    @Test
    void test_getAllContacts() {
        var name = "name";
        var email = "email";
        List<Contact> contacts = mock(List.class);
        List<ContactDto> contactDtos = mock(List.class);

        given(contactRepository.findAll(argThat((Specification<Contact> spec) -> true))).willReturn(contacts);
        given(contactMapper.toList(contacts)).willReturn(contactDtos);

        var returned = contactService.getAllContacts(name, email);

        verify(contactRepository).findAll((Specification<Contact>) ArgumentMatchers.any());
        assertEquals(contactDtos, returned);
    }

    @Test
    void test_saveContact() {
        var contactRequestDto = mock(ContactRequestDto.class);
        var client = mock(Client.class);
        var contact = mock(Contact.class);
        var contactSaved = mock(Contact.class);
        var contactDto = mock(ContactDto.class);

        given(clientRepository.findById(contactRequestDto.getClientId())).willReturn(Optional.of(client));
        given(contactMapper.toEntity(contactRequestDto)).willReturn(contact);
        given(contactRepository.save(argThat((Contact c) -> client.equals(c.getClient())))).willReturn(contactSaved);
        given(contactMapper.toDto(any())).willReturn(contactDto);

        var returned = contactService.saveContact(contactRequestDto);

        verify(clientRepository).findById(contactRequestDto.getClientId());
        InOrder inOrder = inOrder(contactMapper);
        inOrder.verify(contactMapper).toEntity(contactRequestDto);
        verify(contactRepository).save(contact);
        inOrder.verify(contactMapper).toDto(any());
        assertEquals(contactDto, returned);
    }

    @Test
    void test_saveContact_clientNotFound() {
        var contactRequestDto = mock(ContactRequestDto.class);

        given(clientRepository.findById(contactRequestDto.getClientId())).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> contactService.saveContact(contactRequestDto));
    }

    @Test
    void test_saveContact_clientAlreadyHasContact() {
        var contactRequestDto = mock(ContactRequestDto.class);
        var client = mock(Client.class);
        var contact = mock(Contact.class);

        given(clientRepository.findById(contactRequestDto.getClientId())).willReturn(Optional.of(client));
        when(client.getContact()).thenReturn(contact);

        assertThrows(ElementAlreadyExistsException.class, () -> contactService.saveContact(contactRequestDto));
    }

    @Test
    void test_deleteContact() {
        var id = 1;

        contactService.deleteContact(id);

        verify(contactRepository).deleteById(id);
    }
}