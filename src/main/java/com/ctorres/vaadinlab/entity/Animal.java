package com.ctorres.vaadinlab.entity;

import com.ctorres.vaadinlab.animal.Gender;
import com.ctorres.vaadinlab.animal.Specie;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private Specie specie;

    @Column(nullable = true)
    private String personality;

    protected Animal() {}

    public Animal(String name, String image, Gender gender, int age, Specie specie, String personality) {
        this.name = name;
        this.image = image;
        this.gender = gender;
        this.age = age;
        this.specie = specie;
        this.personality = personality;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public Specie getSpecie() {
        return specie;
    }

    public String getPersonality() {
        return personality;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", images=" + image +
                ", gender=" + gender +
                ", age=" + age +
                ", specie=" + specie +
                ", personality='" + personality + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
