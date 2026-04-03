package com.ctorres.vaadinlab.contact.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String fullName;

    @Column
    private int age;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String reasonToAdopt;

    protected Contact() {}

    public Contact(String fullName, int age, String email, String phone, String address, String reasonToAdopt) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.reasonToAdopt = reasonToAdopt;
    }

    public Contact(UUID id, String fullName, int age, String email, String phone, String address, String reasonToAdopt) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.reasonToAdopt = reasonToAdopt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
