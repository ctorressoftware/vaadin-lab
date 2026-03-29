package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        showTitle();
        showAnimalInformation();
    }

    private void showTitle() {
        add(title);
    }

    private void showAnimalInformation() {

        var id = new Div(new HorizontalLayout(
                new Paragraph("ID: "),
                new Paragraph(animal.getId())
        ));
        var name = new Div(new HorizontalLayout(
                new Paragraph("Name: "),
                new Paragraph(animal.getName())
        ));
        var gender = new Div(new HorizontalLayout(
                new Paragraph("Gender: "),
                new Paragraph(animal.getGender().name())
        ));
        var age = new Div(new HorizontalLayout(
                new Paragraph("Age: "),
                new Paragraph(String.valueOf(animal.getAge()))
        ));

        var specie = new Div(new HorizontalLayout(
                new Paragraph("Specie: "),
                new Paragraph(animal.getSpecie().name())
        ));

        var personality = new Div(new HorizontalLayout(
                new Paragraph("Personality: "),
                new Paragraph(animal.getPersonality())
        ));

        var animalPhoto = new Image(animal.getImage(), "Animal photo");
        animalPhoto.setWidth(30f, Unit.PERCENTAGE);

        var photo = new Div(new VerticalLayout(
                new Paragraph("Photo: "),
                animalPhoto
        ));

        add(new VerticalLayout(id, name, gender, age, specie, personality, photo));
    }
}
