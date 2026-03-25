package com.ctorres.vaadinlab.animal;

import java.util.Objects;

public class Animal {
    private final String name;
    private final Gender gender;
    private final int age;
    private final Specie specie;
    private final String personality;

    public Animal(String name, Gender gender, int age, Specie specie, String personality) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.specie = specie;
        this.personality = personality;
    }

    public String getName() {
        return name;
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
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age
                && Objects.equals(name, animal.name)
                && gender == animal.gender
                && specie == animal.specie
                && Objects.equals(personality, animal.personality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, age, specie, personality);
    }
}
