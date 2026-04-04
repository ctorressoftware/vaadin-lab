package com.ctorres.vaadinlab.animal.ui;

import static com.ctorres.vaadinlab.animal.AnimalMessages.*;

import com.ctorres.vaadinlab.animal.entity.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;

import static com.ctorres.vaadinlab.common.DialogHelper.*;

import com.ctorres.vaadinlab.common.Routes;
import static com.ctorres.vaadinlab.common.UIConstants.*;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

@Route(value = Routes.DEFAULT_PAGE)
public class AnimalScreen extends VerticalLayout {
    private final AnimalService animalService;
    private final H1 title = new H1(SCREEN_TITLE);
    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Search");
    private final Button newButton = new Button("New");
    private final Grid<Animal> table = new Grid<>(Animal.class);

    public AnimalScreen(AnimalService animalService) {
        this.animalService = animalService;
        configureLayout();
        configureTitle();
        configureActions();
        configureTable();

        add(title, buildActionBar(), table);
    }

    private void configureTitle() {
        title.setWidthFull();
    }

    private void configureLayout() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    private void configureSearchField() {
        searchField.addKeyPressListener(Key.ENTER, listener -> searchButton.click());
    }

    private void configureSearchButton() {
        searchButton.addClickListener(event -> handleClickSearchButton(
                this::searchAnimalsByName,
                this::reloadAnimalsTable
        ));
    }

    private void handleClickSearchButton(Supplier<List<Animal>> action, Consumer<List<Animal>> onSuccess) {
        try {
            List<Animal> result = action.get();
            onSuccess.accept(result);
        } catch (RuntimeException e) {
            showResultDialog(ERROR_TITLE, ERROR_LOADING_ANIMALS, ERROR_MODAL_WIDTH).open();
        }
    }

    private List<Animal> searchAnimalsByName() {
        String animalName = searchField.getValue();
        return findAnimalsForSearch(animalName);
    }

    private void reloadAnimalsTable(List<Animal> animals) {
        table.setItems(animals);
    }

    private void configureNewButton() {
        newButton.addClickListener(event -> showAnimalDialog(
                animalService::save,
                () -> reloadAnimalsTable(findAllAnimals())
        ).open());
    }

    private AnimalDialog showAnimalDialog(Consumer<Animal> action, Runnable onSuccess) {
        return new AnimalDialog(animal -> {
            try {
                action.accept(animal);
                onSuccess.run();
            } catch (RuntimeException e) {
                showResultDialog(ERROR_TITLE, errorSavingAnimal(animal.getName()), ERROR_MODAL_WIDTH).open();
            }
        });
    }

    private AnimalDialog showAnimalDialog(Animal toEdit, Consumer<Animal> action, Runnable onSuccess) {
        return new AnimalDialog(toEdit, animal -> {
            try {
                action.accept(animal);
                onSuccess.run();
            } catch (RuntimeException e) {
                showResultDialog(ERROR_TITLE, errorEditingAnimal(animal.getName()), ERROR_MODAL_WIDTH).open();
            }
        });
    }

    private void configureTable() {
        setColumnsOrder();
        configureRowActions();
        table.setAllRowsVisible(true);
        table.setColumnReorderingAllowed(true);
        table.setWidth(80f, Unit.PERCENTAGE);
        table.setEmptyStateText(NO_ANIMALS);
        table.addThemeVariants(GridVariant.AURA_COLUMN_BORDERS);
        reloadAnimalsTable(findAllAnimals());
    }

    private void configureRowActions() {

        table.addComponentColumn(animal -> {
            var viewButton = new Button("View", event -> UI.getCurrent()
                    .navigate(AnimalDetails.class, animal.getId().toString()));
            viewButton.getStyle().set("width", "80%");
            viewButton.getStyle().set("background-color", "#FFFBF1");
            return viewButton;
        });

        table.addComponentColumn(animal -> {
            var editButton = new Button("Edit", event -> showAnimalDialog(
                    animal,
                    animalService::editAnimal,
                    () -> reloadAnimalsTable(findAllAnimals())
            ).open());
            editButton.getStyle().set("width", "80%");
            editButton.getStyle().set("background-color", "#F3BE7A");
            return editButton;
        });

        table.addComponentColumn(animal -> {
            var deleteButton = new Button("Delete", event -> showConfirmDialog(
                    () -> animalService.deleteAnimal(animal.getId()),
                    () -> reloadAnimalsTable(findAllAnimals())
            ).open());
            deleteButton.getStyle().set("width", "80%");
            deleteButton.getStyle().set("background-color", "#FF5A5A");
            return deleteButton;
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
                : animalService.findAnimalsByNameContaining(normalizedQuery);
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
        try {
            return animalService.findAllAnimals();
        } catch (RuntimeException e) {
            showResultDialog(ERROR_TITLE, ERROR_LOADING_ANIMALS, ERROR_MODAL_WIDTH).open();
        }
        return List.of();
    }
}
