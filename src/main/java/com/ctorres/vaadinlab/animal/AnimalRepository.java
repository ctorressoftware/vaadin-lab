package com.ctorres.vaadinlab.animal;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Repository
public class AnimalRepository {

    private final Set<Animal> animalsForAdoption = new HashSet<>(0);

    public AnimalRepository() {
        animalsForAdoption.add(
                new Animal(
                        "Rufus",
                        Gender.MALE,
                        1,
                        Specie.DOG,
                        "Good buddy")
        );
    }

    public Animal save(Animal animal) {

        if (!isValid(animal)) return null;

        boolean saved = animalsForAdoption.add(animal);

        if (saved) {
            return animal;
        } else {
            throw new RuntimeException("An error occurred trying to save: " + animal);
        }
    }

    public Set<Animal> findAll() {
        return animalsForAdoption;
    }

    private boolean isValid(Animal animal) {

        if (animal == null) {
            throw new RuntimeException("Animal is required");
        }

        if (animal.getName() == null || animal.getName().isBlank()) {
            throw new RuntimeException("name property is required");
        }

        if (!Arrays.stream(Gender.values()).toList().contains(animal.getGender())) {
            throw new RuntimeException("gender property is required");
        }

        if (animal.getAge() < 0) {
            throw new RuntimeException("age property is required");
        }

        if (!Arrays.stream(Specie.values()).toList().contains(animal.getSpecie())) {
            throw new RuntimeException("specie property is required");
        }

        if (animal.getPersonality().isBlank()) {
            throw new RuntimeException("personality property is required");
        }

        return true;
    }
}
