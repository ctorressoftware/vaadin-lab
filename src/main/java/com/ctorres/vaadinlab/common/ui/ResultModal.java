package com.ctorres.vaadinlab.common.ui;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;

public class ResultModal extends Dialog {
    private final String title;
    private final String result;

    public ResultModal(String title, String result, float width) {
        this.title = title;
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
        setHeaderTitle(title);
        setWidth(width, Unit.PERCENTAGE);
        add(new Paragraph(result));
    }
}

