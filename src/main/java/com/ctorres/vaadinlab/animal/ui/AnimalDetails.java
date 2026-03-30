package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.vaadin.flow.component.Component;
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

    private <T extends Component> Div createContentFrame(String title, T data, boolean isVertical) {
        var header = new Paragraph(title);
        var content = isVertical ?
                new VerticalLayout(header, data) :
                new HorizontalLayout(header, data);
        return new Div(content);
    }

    private void showAnimalInformation() {
        var id = createContentFrame("ID:", new Paragraph(animal.getId()), false);
        var name = createContentFrame("Name:", new Paragraph(animal.getName()), false);
        var gender = createContentFrame("Gender:", new Paragraph(animal.getGender().name()), false);
        var age = createContentFrame("Age:", new Paragraph(String.valueOf(animal.getAge())), false);
        var specie = createContentFrame("Specie:", new Paragraph(animal.getSpecie().name()), false);
        var personality = createContentFrame("Personality:", new Paragraph(animal.getPersonality()), false);
        var photo = createContentFrame("Photo:", animal.getImage() == null ?
                        new Paragraph("No image") :
                        setPhotoDimensions(animal.getImage(), "Animal photo"),
                animal.getImage() != null);

        add(new VerticalLayout(id, name, gender, age, specie, personality, photo));
    }

    private Image setPhotoDimensions(String url, String alt) {
        var image = new Image(url, alt);
        image.setWidth(30f, Unit.PERCENTAGE);
        return image;
    }
}
