package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.ctorres.vaadinlab.animal.Gender;
import com.ctorres.vaadinlab.animal.Specie;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import java.util.Optional;

public class AnimalForm extends FormLayout {
    private final TextField nameField = new TextField("Name");
    private final ComboBox<String> genderComboBox =
            new ComboBox<>("Gender", List.of(Gender.MALE.name(), Gender.FEMALE.name()));
    private final IntegerField ageField = new IntegerField("Age");
    private final ComboBox<String> specieComboBox =
            new ComboBox<>("Specie", List.of(Specie.CAT.name(), Specie.DOG.name()));
    private final TextField personalityField = new TextField("Personality");

    public AnimalForm() {
        configure();
    }

    private void configure() {
        setAutoResponsive(true);
        addFormRow(nameField);
        addFormRow(genderComboBox);
        addFormRow(ageField);
        addFormRow(specieComboBox);
        addFormRow(personalityField);
    }

    public Optional<Animal> getFormDataObject() {
        final String name = nameField.getValue();
        final String gender = genderComboBox.getValue();
        final int age = ageField.getValue();
        final String specie = specieComboBox.getValue();
        final String personality = personalityField.getValue();

        return Optional.of(new Animal(name, Gender.valueOf(gender), age, Specie.valueOf(specie), personality));
    }
}
