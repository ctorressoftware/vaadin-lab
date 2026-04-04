package com.ctorres.petadoptionui.animal.ui;

import com.ctorres.petadoptionui.animal.entity.Animal;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.function.SerializableConsumer;

import static com.ctorres.petadoptionui.animal.AnimalMessages.ERROR_COMPLETING_FORM_FIELDS;
import static com.ctorres.petadoptionui.common.UIConstants.FORM_DIALOG_WIDTH;

public class AnimalDialog extends Dialog {
    private final SerializableConsumer<Animal> onSaveCallback;
    private final AnimalForm form;

    public AnimalDialog(SerializableConsumer<Animal> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
        this.form = new AnimalForm();
        configureModalDialog("Add animal");
        configureButtons();
    }

    public AnimalDialog(Animal animalToEdit, SerializableConsumer<Animal> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
        this.form = new AnimalForm(animalToEdit);
        configureModalDialog("Edit animal");
        configureButtons();
    }

    private void configureButtons() {
        var saveButton = new Button("Save", event -> save());
        var cancelButton = new Button("Cancel", event -> close());

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addClassName("action-button");
        saveButton.addClassName("action-button");

        getFooter().add(cancelButton, saveButton);
    }

    private void configureModalDialog(String headerTitle) {
        setHeaderTitle(headerTitle);
        setWidth(FORM_DIALOG_WIDTH, Unit.PERCENTAGE);
        add(form);
    }

    private void save() {
        var animal = form.getFormData();
        if (animal == null) {
            Notification.show(ERROR_COMPLETING_FORM_FIELDS);
            return;
        }
        onSaveCallback.accept(animal);
        close();
    }
}
