package com.ctorres.vaadinlab.contact;

import com.ctorres.vaadinlab.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {}
