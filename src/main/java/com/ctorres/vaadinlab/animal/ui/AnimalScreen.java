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

        var toolbarCard = new Div(buildActionBar());
        toolbarCard.addClassName("toolbar-card");

        var tableCard = new Div(table);
        tableCard.addClassName("table-card");

        add(title, toolbarCard, tableCard);
    }

    private void configureTitle() {
        title.setWidthFull();
        title.addClassName("screen-title");
    }

    private void configureLayout() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        addClassName("app-screen");
    }

    private void configureSearchField() {
        searchField.addKeyPressListener(Key.ENTER, listener -> searchButton.click());
    }

    private void configureSearchButton() {
        searchButton.addClassNames("action-button", "search-btn");
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
        newButton.addClassNames("action-button", "new-btn");
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
        table.setColumnReorderingAllowed(false);
        table.setWidthFull();
        table.addClassName("table-card");
        table.setEmptyStateText(NO_ANIMALS);
        table.addThemeVariants(GridVariant.AURA_COLUMN_BORDERS);
        reloadAnimalsTable(findAllAnimals());
    }

    private void configureRowActions() {

        table.addComponentColumn(animal -> {
            var viewButton = new Button("View", event -> UI.getCurrent()
                    .navigate(AnimalDetails.class, animal.getId().toString()));
            viewButton.addClassName("view-btn");
            return viewButton;
        });

        table.addComponentColumn(animal -> {
            var editButton = new Button("Edit", event -> showAnimalDialog(
                    animal,
                    animalService::editAnimal,
                    () -> reloadAnimalsTable(findAllAnimals())
            ).open());
            editButton.addClassName("edit-btn");
            return editButton;
        });

        table.addComponentColumn(animal -> {
            var deleteButton = new Button("Delete", event -> showConfirmDialog(
                    () -> animalService.deleteAnimal(animal.getId()),
                    () -> reloadAnimalsTable(findAllAnimals())
            ).open());
            deleteButton.addClassName("delete-btn");
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
        newButton.addClassNames("action-button");
        searchButton.addClassNames("action-button");
        searchField.addClassName("action-search");
        searchField.setPlaceholder("Rufus");

        configureSearchButton();
        configureNewButton();
        configureSearchField();
    }

    private HorizontalLayout buildActionBar() {
        var actionBar = new HorizontalLayout(searchField, searchButton, newButton);
        actionBar.addClassName("action-bar");
        actionBar.setWidthFull();
        return actionBar;
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
