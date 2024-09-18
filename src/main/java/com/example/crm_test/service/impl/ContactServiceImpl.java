package com.example.crm_test.service.impl;

import com.example.crm_test.dto.ContactDto;
import com.example.crm_test.dto.ContactRequestDto;
import com.example.crm_test.exception.ElementAlreadyExistsException;
import com.example.crm_test.mapper.ContactMapper;
import com.example.crm_test.model.Contact;
import com.example.crm_test.repository.ClientRepository;
import com.example.crm_test.repository.ContactRepository;
import com.example.crm_test.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.crm_test.repository.ContactRepository.Specs.byEmailLike;
import static com.example.crm_test.repository.ContactRepository.Specs.byNameLike;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDto getContactById(Integer id) {
        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No contact found with id: " + id));
        return contactMapper.toDto(contact);
    }

    @Override
    public List<ContactDto> getAllContacts(String name, String email) {
        Specification<Contact> specification = Specification
                .where(byNameLike(name))
                .and(byEmailLike(email));

        var contacts = contactRepository.findAll(specification);
        return contactMapper.toList(contacts);
    }

    @Override
    public ContactDto saveContact(ContactRequestDto contactDto) {
        var client = clientRepository.findById(contactDto.getClientId())
                .orElseThrow(() -> new NoSuchElementException("No client found with id: " + contactDto.getClientId()));
        if (client.getContact() != null) {
            throw new ElementAlreadyExistsException("Contact with id: " + contactDto.getClientId()
                    + " already assign to client with id: " + client.getId());
        }
        var contact = contactMapper.toEntity(contactDto);
        contact.setClient(client);
        var saved = contactRepository.save(contact);

        return contactMapper.toDto(saved);
    }

    @Override
    public void deleteContact(Integer id) {
        contactRepository.deleteById(id);
    }
}
