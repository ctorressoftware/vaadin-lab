package com.ctorres.vaadinlab.animal;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Animal> findAnimalsByName(String name) {
        if (name == null || name.isBlank()) throw new RuntimeException("name is required");
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getName().toLowerCase().contains(name.toLowerCase().trim()))
                .toList();
    }

    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal save(String name, Gender gender, int age, Specie specie, String personality) {
        var animal = new Animal(name, gender, age, specie, personality);
        return animalRepository.save(animal);
    }
}