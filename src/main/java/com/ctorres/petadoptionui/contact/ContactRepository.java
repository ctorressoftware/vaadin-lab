package com.ctorres.petadoptionui.contact;

import com.ctorres.petadoptionui.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {}
