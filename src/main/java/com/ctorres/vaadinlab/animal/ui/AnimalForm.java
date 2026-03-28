package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.Gender;
import com.ctorres.vaadinlab.animal.Specie;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Optional;

public class AnimalForm extends FormLayout {
    private final TextField nameField = new TextField("Name");
    private final ComboBox<Gender> genderComboBox = new ComboBox<>("Gender");
    private final IntegerField ageField = new IntegerField("Age");
    private final ComboBox<Specie> specieComboBox = new ComboBox<>("Specie");
    private final TextArea personalityField = new TextArea("Personality");

    public AnimalForm() {
        configureForm();
        configureFields();
        setDefaultComboboxValues();
        addFields();
    }

    private void configureForm() {
        setWidthFull();
        setResponsiveSteps(new ResponsiveStep("0", 1));
    }

    private void configureFields() {
        nameField.setWidthFull();
        genderComboBox.setWidthFull();
        ageField.setWidthFull();
        specieComboBox.setWidthFull();
        personalityField.setWidthFull();

        genderComboBox.setItems(Gender.values());
        specieComboBox.setItems(Specie.values());
    }

    private void setDefaultComboboxValues() {
        genderComboBox.setValue(Gender.MALE);
        specieComboBox.setValue(Specie.DOG);
    }

    private void addFields() {
        add(nameField, genderComboBox, ageField, specieComboBox, personalityField);
    }

    public Optional<Animal> getFormDataObject() {
        final String name = nameField.getValue();
        final Gender gender = genderComboBox.getValue();
        final Integer age = ageField.getValue();
        final Specie specie = specieComboBox.getValue();
        final String personality = personalityField.getValue();

        if (name == null || gender == null || age == null || specie == null || personality == null) {
            return Optional.empty();
        }

        return Optional.of(new Animal(
                name,
                gender,
                age,
                specie,
                personality
        ));
    }
}
