package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("/")
public class AnimalScreen extends VerticalLayout {
    private final AnimalService animalService;
    private final H1 title = new H1("Animals for adoption");
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Search");
    private final Button newButton = new Button("New");
    private final Grid<Animal> table = new Grid<>(Animal.class);

    public AnimalScreen(AnimalService animalService) {
        this.animalService = animalService;
        configureLayout();
        configureActions();
        configureTable();

        add(title, buildActionBar(), table);
    }

    private void configureLayout() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    private void configureSearchField() {
        searchField.addKeyPressListener(Key.ENTER, listener -> {
            searchButton.click();
        });
    }

    private void configureSearchButton() {
        searchButton.addClickListener(event -> {
            String animalName = searchField.getValue();
            List<Animal> animalsToAdopt = findAnimalsForSearch(animalName);
            refreshTable(animalsToAdopt);
        });
    }

    private void refreshTable(List<Animal> animals) {
        table.setItems(animals);
    }

    private void configureNewButton() {
        newButton.addClickListener(event -> new AnimalDialog(animal -> {
            animalService.save(animal);
            refreshTable(findAllAnimals());
        }).open());
    }

    private void configureTable() {
        setColumnsOrder();
        table.setAllRowsVisible(true);
        table.setColumnReorderingAllowed(true);
        table.setEmptyStateText("No animals to adopt");
        table.addThemeVariants(GridVariant.AURA_COLUMN_BORDERS);
        refreshTable(findAllAnimals());

    }

    private void setColumnsOrder() {
        var nameColumn = table.getColumnByKey("name");
        var columns = new ArrayList<>(table.getColumns().stream()
                .filter(column -> !column.getKey().equals(nameColumn.getKey()))
                .toList());
        columns.addFirst(nameColumn);
        table.setColumnOrder(columns);
    }

    private List<Animal> findAnimalsForSearch(String query) {
        String normalizedQuery = query == null ? "" : query.trim();
        return normalizedQuery.isBlank()
                ? animalService.findAllAnimals()
                : animalService.findAnimalsByName(normalizedQuery);
    }

    private void configureActions() {
        searchField.setPlaceholder("Rufus");
        searchField.getStyle().set("height", "30px");
        searchButton.getStyle().set("margin", "0px 10px");
        configureSearchButton();
        configureNewButton();
        configureSearchField();
    }

    private HorizontalLayout buildActionBar() {
        return new HorizontalLayout(searchField, searchButton, newButton);
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
