package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalRepository;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;

import java.util.Set;
import java.util.stream.Collectors;

@Route("/animals")
public class AnimalScreen extends Div {

    private final AnimalRepository animalRepository;

    public AnimalScreen(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
        draw();
    }

    private void showTitle() {
        add(new H2("Animals for adoption"));
    }

    private void showAnimals(Set<Animal> animals) {

        if (animals == null) throw new RuntimeException("animals is required");
        if (animals.isEmpty()) add("There are no animals for adoption :(");

        var table = new NativeTable();
        table.add(new NativeTableHeader(
                new NativeTableRow(
                        new NativeTableHeaderCell("name"),
                        new NativeTableHeaderCell("gender"),
                        new NativeTableHeaderCell("age"),
                        new NativeTableHeaderCell("specie"),
                        new NativeTableHeaderCell("personality")
                )
        ));

        table.addBody().add(animals.stream().map(animal ->
                new NativeTableRow(
                        new NativeTableCell(animal.getName()),
                        new NativeTableCell(animal.getGender().name()),
                        new NativeTableCell(String.valueOf(animal.getAge())),
                        new NativeTableCell(animal.getSpecie().name()),
                        new NativeTableCell(animal.getPersonality())
                )
        ).collect(Collectors.toList()));
        add(table);
    }

    private void draw() {
        showTitle();
        showAnimals(animalRepository.findAll());
    }

}
