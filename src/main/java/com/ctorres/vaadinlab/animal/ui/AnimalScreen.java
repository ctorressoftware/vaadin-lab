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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route("/animals")
public class AnimalScreen extends VerticalLayout {
    private final AnimalService animalService;
    private final H1 title = new H1("Animals for adoption");
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Search");
    private final Button newButton = new Button("New");
    private final NativeTable table = new NativeTable(); // TODO: implement Grid

    public AnimalScreen(AnimalService animalService) {
        this.animalService = animalService;
        configureLayout();
        configureActionBar();
        configureTable();

        add(title, buildActionBar(), table);
    }

    private void configureLayout() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    private void configureSearchButton() {
        searchButton.addClickListener(event -> {
            String animalName = searchField.getValue();
            List<Animal> animalsToAdopt = findAnimalsForSearch(animalName);
            refreshTable(animalsToAdopt);
        });
    }

    private void refreshTable(List<Animal> animals) {
        table.removeBody();
        table.addBody().add(buildTableContent(animals));
    }

    private void configureNewButton() {
        newButton.addClickListener(event ->
            Notification.show("New button listener is configured!")
        );
    }

    private void configureTable() {
        table.getHead().add(buildTableHeader());
        refreshTable(findAllAnimals());
    }

    private List<Animal> findAnimalsForSearch(String query) {
        String normalizedQuery = query == null ? "" : query.trim();
        return normalizedQuery.isBlank()
                ? animalService.findAllAnimals()
                : animalService.findAnimalsByName(normalizedQuery);
    }

    private void configureActionBar() {
        searchField.setPlaceholder("Rufus");
        searchField.getStyle().set("height", "30px");
        searchButton.getStyle().set("margin", "0px 10px");
        configureSearchButton();
        configureNewButton();
    }

    private Div buildActionBar() {
        return new Div(new HorizontalLayout(searchField, searchButton, newButton));
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

    private List<Animal> findAllAnimals() {
        return animalService.findAllAnimals();
    }
}
