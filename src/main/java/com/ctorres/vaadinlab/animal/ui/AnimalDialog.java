package com.ctorres.vaadinlab.animal.ui;


import com.ctorres.vaadinlab.animal.Animal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableConsumer;

public class AnimalEditor extends Dialog {

    private final SerializableConsumer<Animal> onSaveCallback;
    // private final ProposalForm form;

    public AnimalEditor(SerializableConsumer<Animal> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;

        // Create the components
        // form = new ProposalForm();

        var saveBtn = new Button("Create Proposal", event -> save());
        saveBtn.addThemeVariants(ButtonVariant.AURA_PRIMARY);

        var cancelBtn = new Button("Cancel", event -> close());

        // Configure the dialog
        setHeaderTitle("New Proposal");
        add(new TextField("Prueba"));
        getFooter().add(cancelBtn, saveBtn);
    }

    private void save() {
        //form.getFormDataObject().ifPresent(proposal -> {
        //    onSaveCallback.accept(proposal);
        //    close();
        //});
    }
}
