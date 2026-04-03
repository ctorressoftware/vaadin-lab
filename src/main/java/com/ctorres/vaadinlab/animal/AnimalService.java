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

    public List<Animal> findAnimalsByNameContaining(String name) {
        if (name == null || name.isBlank()) throw new RuntimeException("name is required");
        return animalRepository.findByNameContainingIgnoreCase(name.trim());
    }

    public Optional<Animal> findAnimalById(UUID id) {
        if (id == null) throw new RuntimeException("id is required");
        return animalRepository.findById(id);
    }

    public void editAnimal(Animal animal) {
        boolean animalExists = animalRepository.existsAnimalById(animal.getId());
        if (!animalExists) throw new RuntimeException("Specified animal doesn't exist. ID=" + animal.getId());
        animalRepository.save(animal);
    }

    public void deleteAnimal(UUID id) {
        animalRepository.deleteById(id);
    }

    public void save(Animal animal) {
        animalRepository.save(animal);
    }
}