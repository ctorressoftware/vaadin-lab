package com.ctorres.vaadinlab.animal;

import com.ctorres.vaadinlab.entity.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AnimalRepository {

    private final List<Animal> animalsForAdoption = new ArrayList<>(0);

    public AnimalRepository() {
        animalsForAdoption.add(new Animal("Rufus", null, Gender.MALE, 1, Specie.DOG, "Angry dog"));
        animalsForAdoption.add(new Animal("Markus", "https://upload.wikimedia.org/wikipedia/commons/9/9a/Cat_07464_kalamis_safinaz.jpg", Gender.MALE, 3, Specie.CAT, "Boring cat"));
        animalsForAdoption.add(new Animal("Firulasha", null, Gender.FEMALE, 6, Specie.DOG, "Cute dog"));
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

    public Animal findAnimalByName(String name) {
        return animalsForAdoption.stream()
                .filter(animal -> animal.getName().equals(name))
                .toList()
                .getFirst();
    }

    public List<Animal> findAll() {
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
