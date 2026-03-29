package com.ctorres.vaadinlab.animal;

import java.util.Objects;
import java.util.UUID;

public class Animal {
    private final String id = UUID.randomUUID().toString();
    private final String name;
    private final String image;
    private final Gender gender;
    private final int age;
    private final Specie specie;
    private final String personality;

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
        return age == animal.age
                && Objects.equals(id, animal.id)
                && Objects.equals(name, animal.name)
                && Objects.equals(image, animal.image)
                && gender == animal.gender
                && specie == animal.specie
                && Objects.equals(personality, animal.personality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, gender, age, specie, personality);
    }
}
