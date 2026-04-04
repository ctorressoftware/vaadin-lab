package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.entity.Animal;
import com.ctorres.vaadinlab.animal.Gender;
import com.ctorres.vaadinlab.animal.Specie;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Arrays;
import java.util.UUID;

import static com.ctorres.vaadinlab.animal.AnimalMessages.URL_REGEX_PATTERN;

public class AnimalForm extends FormLayout {
    private final TextField nameField = new TextField();
    private final TextField imageUrlField = new TextField();
    private final ComboBox<Gender> genderComboBox = new ComboBox<>();
    private final IntegerField ageField = new IntegerField();
    private final ComboBox<Specie> specieComboBox = new ComboBox<>();
    private final TextArea personalityField = new TextArea();
    private UUID id = null;

    public AnimalForm() {
        configureForm();
        configureFields();
        configureCombobox();
        addFields();
    }

    public AnimalForm(Animal animalToEdit) {
        configureForm();
        configureFields();
        configureCombobox();
        configureAnimalToEdit(animalToEdit);
        addFields();
    }

    private void configureForm() {
        setWidthFull();
        setResponsiveSteps(new ResponsiveStep("0", 1));
        addClassName("form-section");
    }

    private void configureFields() {
        nameField.setWidthFull();
        nameField.setRequired(true);
        nameField.setPlaceholder("Rufus");
        imageUrlField.setWidthFull();
        imageUrlField.setRequired(true);
        imageUrlField.setPlaceholder("https://my-image.com/lorem-ipsum.jpg");
        ageField.setWidthFull();
        ageField.setRequired(true);
        ageField.setPlaceholder("1");
        personalityField.setWidthFull();
        personalityField.setPlaceholder("A super cute cat! :D");
    }

    private void configureAnimalToEdit(Animal animalToEdit) {
        id = animalToEdit.getId();
        nameField.setValue(animalToEdit.getName());
        imageUrlField.setValue(animalToEdit.getImage());
        ageField.setValue(animalToEdit.getAge());
        genderComboBox.setValue(animalToEdit.getGender());
        specieComboBox.setValue(animalToEdit.getSpecie());
        personalityField.setValue(animalToEdit.getPersonality());
    }

    private void clearErrors() {
        nameField.getStyle().set("border", "");
        imageUrlField.getStyle().set("border", "");
        ageField.getStyle().set("border", "");
        genderComboBox.getStyle().set("border", "");
        specieComboBox.getStyle().set("border", "");
    }

    private void configureCombobox() {
        genderComboBox.setWidthFull();
        genderComboBox.setRequired(true);
        specieComboBox.setWidthFull();
        specieComboBox.setRequired(true);
        genderComboBox.setItems(Gender.values());
        specieComboBox.setItems(Specie.values());
        genderComboBox.setPlaceholder("Select animal gender");
        specieComboBox.setPlaceholder("Select animal specie");
    }

    private void addFields() {
        addFormItem(nameField, "Name");
        addFormItem(imageUrlField, "Image");
        addFormItem(genderComboBox, "Gender");
        addFormItem(ageField, "Age");
        addFormItem(specieComboBox, "Specie");
        addFormItem(personalityField, "Personality");
    }
    
    private boolean validate() {
        clearErrors();
        boolean result = true;
        if (nameField.isRequired() && nameField.getValue().isBlank()) {
            nameField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (imageUrlField.isRequired()) {
            if (imageUrlField.getValue().isBlank() || !imageUrlField.getValue().matches(URL_REGEX_PATTERN)) {
                imageUrlField.getStyle().set("border", "1px solid red");
                result = false;
            }
        }

        if (genderComboBox.isRequired() &&
                Arrays.stream(Gender.values()).noneMatch(g -> g.equals(genderComboBox.getValue()))) {
            genderComboBox.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (ageField.isRequired() && (ageField.getValue() == null || ageField.getValue() <= 0)) {
            ageField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (specieComboBox.isRequired() &&
                Arrays.stream(Specie.values()).noneMatch(g -> g.equals(specieComboBox.getValue()))) {
            specieComboBox.getStyle().set("border", "1px solid red");
            result = false;
        }

        return result;
    }

    public Animal getFormData() {

        if (!validate()) {
            return null;
        }

        return new Animal(
                id,
                nameField.getValue(),
                imageUrlField.getValue(),
                genderComboBox.getValue(),
                ageField.getValue(),
                specieComboBox.getValue(),
                personalityField.getOptionalValue().orElse("No data")
        );
    }
}
