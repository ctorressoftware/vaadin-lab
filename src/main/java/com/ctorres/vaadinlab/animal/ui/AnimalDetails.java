package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.Optional;

@Route(value = "/details")
public class AnimalDetails extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    private Animal animal;
    private final AnimalService animalService;
    private final H2 title = new H2("Animal details");

    public AnimalDetails(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        if (parameter == null || parameter.isBlank()) throw new RuntimeException("name parameter is required");
        this.animal = Optional.ofNullable(animalService.findAnimalByName(parameter))
                .orElseThrow(() -> new RuntimeException("Animal doesn't exists. Name =" + parameter));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        removeAll();
        add (title);
        add(animal.toString());
    }
}
