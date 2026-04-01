package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.entity.Animal;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.function.SerializableConsumer;

public class AnimalDialog extends Dialog {
    private final SerializableConsumer<Animal> onSaveCallback;
    private final AnimalForm form;

    public AnimalDialog(SerializableConsumer<Animal> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
        this.form = new AnimalForm();
        configureModalDialog();
        configureButtons();
    }

    private void configureButtons() {
        var saveButton = new Button("Save", event -> save());
        var cancelButton = new Button("Cancel", event -> close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(cancelButton, saveButton);
    }

    private void configureModalDialog() {
        setHeaderTitle("Add animal");
        setWidth(40f, Unit.PERCENTAGE);
        add(form);
    }

    private void save() {
        form.getFormDataObject().ifPresent(animal -> {
            onSaveCallback.accept(animal);
            close();
        });
    }
}
