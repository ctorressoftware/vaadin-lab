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
}
