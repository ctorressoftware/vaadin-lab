package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.Animal;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.function.SerializableConsumer;

// TODO: clean
public class AnimalDialog extends Dialog {
    private final SerializableConsumer<Animal> onSaveCallback;
    private final AnimalForm form;

    public AnimalDialog(SerializableConsumer<Animal> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
        this.form = new AnimalForm();
        var saveBtn = new Button("Save", event -> save());
        saveBtn.addThemeVariants(ButtonVariant.AURA_PRIMARY);
        var cancelBtn = new Button("Cancel", event -> close());

        // Configure the dialog
        setHeaderTitle("Add animal");
        setWidth(40f, Unit.PERCENTAGE);
        add(form);
        getFooter().add(cancelBtn, saveBtn);
    }

    private void save() {
        form.getFormDataObject().ifPresent(animal -> {
            onSaveCallback.accept(animal);
            close();
        });
    }
}
