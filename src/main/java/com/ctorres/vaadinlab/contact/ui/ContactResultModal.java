package com.ctorres.vaadinlab.contact.ui;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.function.SerializableConsumer;

public class ContactResultModal extends Dialog {

    public ContactResultModal(SerializableConsumer<Void> onAcceptCallback) {
        this.configureModalDialog();
        this.configureButtons();
    }

    private void configureButtons() {
        var acceptButton = new Button("Accept", event -> accept());
        acceptButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(acceptButton);
    }

    private void configureModalDialog() {
        setHeaderTitle("Contact created successfully!");
        setWidth(20f, Unit.PERCENTAGE);
        add(new Paragraph(
                """
                        We registered your contact information successfully.
                        You will be contact in the next 24 hours.
                        """));
    }

    private void accept() {
        close();
    }
}

