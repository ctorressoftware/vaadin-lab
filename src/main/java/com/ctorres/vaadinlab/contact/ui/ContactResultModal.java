package com.ctorres.vaadinlab.contact.ui;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;

public class ContactResultModal extends Dialog {
    private final boolean success;
    private final String result;

    public ContactResultModal(boolean success, String result, float width) {
        this.success = success;
        this.result = result;
        this.configureModalDialog(width);
        this.configureButtons();
    }

    private void configureButtons() {
        var acceptButton = new Button("Accept", event -> close());
        acceptButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(acceptButton);
    }

    private void configureModalDialog(float width) {
        setHeaderTitle(success ? "Contact created successfully!" : "Oops! Something bad occurred :(");
        setWidth(width, Unit.PERCENTAGE);
        add(new Paragraph(result));
    }
}

