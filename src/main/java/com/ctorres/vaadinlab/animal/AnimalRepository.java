package com.ctorres.vaadinlab.animal;

import com.ctorres.vaadinlab.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    Optional<Animal> findAnimalByName(String name);
}
