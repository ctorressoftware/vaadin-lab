package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayoutVariant;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route("/animals")
public class AnimalScreen extends VerticalLayout {
    private final AnimalService animalService;
    private final H1 title = new H1("Animals for adoption");
    private final Input searchInput = new Input();
    private final Button searchButton = new Button("Search");
    private final Button newButton = new Button("New");

    public AnimalScreen(AnimalService animalService) {
        this.animalService = animalService;

        configureLayout();
        configureActionBar();

        add(title, buildActionBar(), buildAnimalsTable(doFirstAnimalLoad()));
    }

    private void configureLayout() {
        setThemeVariant(VerticalLayoutVariant.LUMO_WRAP, true);
    }

    private void configureClickToSearchButton() {
        searchButton.addClickListener(ClickEventListener -> {
            Notification.show("Search button listener is configured!");
        });
    }

    private void configureClickToNewButton() {
        newButton.addClickListener(ClickEventListener -> {
            Notification.show("New button listener is configured!");
        });
    }

    private List<Animal> loadAnimalsByName(String name) {
        return animalService.findAllAnimalsByName(name);
    }

    private void configureActionBar() {
        searchInput.setPlaceholder("Rufus");
        searchInput.getStyle().set("height", "30px");
        searchButton.setClassName(ButtonVariant.LUMO_ICON.getVariantName());
        searchButton.getStyle().set("margin", "0px 10px");
        configureClickToNewButton();
        configureClickToSearchButton();
    }

    private Div buildActionBar() {
        var div = new Div(new HorizontalLayout());
        div.add(searchInput, searchButton, newButton);
        return div;
    }

    private NativeTableHeader buildTableHeader() {
        var name = new NativeTableHeaderCell("name");
        var gender = new NativeTableHeaderCell("gender");
        var age = new NativeTableHeaderCell("age");
        var specie = new NativeTableHeaderCell("specie");
        var personality = new NativeTableHeaderCell("personality");
        var row = new NativeTableRow(name, gender, age, specie, personality);
        return new NativeTableHeader(row);
    }

    private NativeTableBody buildTableContent(List<Animal> animals) {

        var content = new NativeTableBody();
        List<Component> rows = animals.stream()
                .map(animal -> new NativeTableRow(
                        new NativeTableCell(animal.getName()),
                        new NativeTableCell(animal.getGender().name()),
                        new NativeTableCell(String.valueOf(animal.getAge())),
                        new NativeTableCell(animal.getSpecie().name()),
                        new NativeTableCell(animal.getPersonality())))
                .collect(Collectors.toList());

        content.add(rows);
        return content;
    }

    private List<Animal> doFirstAnimalLoad() {
        return animalService.findAllAnimals();
    }

    private NativeTable buildAnimalsTable(List<Animal> animalsToAdopt) {
        return new NativeTable(buildTableHeader(), buildTableContent(animalsToAdopt));
    }
}
