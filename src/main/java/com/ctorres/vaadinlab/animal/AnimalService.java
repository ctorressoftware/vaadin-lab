package com.ctorres.vaadinlab.animal;

import com.ctorres.vaadinlab.animal.entity.Animal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }

    // TODO: Query animals by name directly in the database instead of loading all records first.
    public List<Animal> findAnimalsByName(String name) {
        if (name == null || name.isBlank()) throw new RuntimeException("name is required");
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getName().toLowerCase().contains(name.toLowerCase().trim()))
                .toList();
    }

    public Optional<Animal> findAnimalByName(String name) {
        if (name == null || name.isBlank()) throw new RuntimeException("name is required");
        return animalRepository.findAnimalByName(name);
    }

    public void editAnimal(Animal animal) {
        boolean animalExists = animalRepository.existsAnimalById(animal.getId());
        if (!animalExists) throw new RuntimeException("Specified animal doesn't exist. ID=" + animal.getId());
        animalRepository.save(animal);
    }

    public void deleteAnimal(UUID id) {
        animalRepository.deleteById(id);
    }

    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }
}