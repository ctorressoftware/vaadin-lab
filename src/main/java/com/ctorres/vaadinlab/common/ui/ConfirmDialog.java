package com.ctorres.vaadinlab.common.ui;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.function.SerializableConsumer;

public class ConfirmDialog extends Dialog {
    private final SerializableConsumer<Boolean> onConfirmCallback;

    public ConfirmDialog(SerializableConsumer<Boolean> onConfirmCallback) {
        this.onConfirmCallback = onConfirmCallback;
        this.configureModalDialog();
        this.configureButtons();
    }

    private void configureButtons() {
        var confirmButton = new Button("Confirm", event -> confirm());
        var cancelButton = new Button("Cancel", event -> close());
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getFooter().add(cancelButton, confirmButton);
    }

    private void configureModalDialog() {
        setHeaderTitle("Confirm modal");
        setWidth(20f, Unit.PERCENTAGE);
        add(new Paragraph("Are you sure to delete the selected row?"));
    }

    private void confirm() {
        onConfirmCallback.accept(true);
        close();
    }
}
