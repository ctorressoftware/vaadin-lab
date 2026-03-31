package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
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

@Route(value = "/")
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
        configureTableActions();
        table.setAllRowsVisible(true);
        table.setColumnReorderingAllowed(true);
        table.setWidth(100f, Unit.PERCENTAGE);
        table.setEmptyStateText("No animals to adopt");
        table.addThemeVariants(GridVariant.AURA_COLUMN_BORDERS);
        refreshTable(findAllAnimals());
    }

    private void configureTableActions() {
        table.addComponentColumn(animal -> {
            var editButton = new Button("edit", clickEvent -> { /* TODO */ });
            var deleteButton = new Button("delete", clickEvent -> { /* TODO */ });
            var viewButton = new Button("view", clickEvent -> UI.getCurrent()
                    .navigate(AnimalDetails.class, animal.getName()));
            return new HorizontalLayout(editButton, viewButton, deleteButton);
        });
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

    private List<Animal> findAllAnimals() {
        return animalService.findAllAnimals();
    }
}