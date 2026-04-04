package com.ctorres.petadoptionui.animal;

import com.ctorres.petadoptionui.animal.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    boolean existsAnimalById(UUID id);
    List<Animal> findByNameContainingIgnoreCase(String name);
}
