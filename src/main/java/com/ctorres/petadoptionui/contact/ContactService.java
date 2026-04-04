package com.ctorres.petadoptionui.contact;

import com.ctorres.petadoptionui.contact.entity.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

}
