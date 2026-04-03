package com.ctorres.vaadinlab.contact.ui;

import com.ctorres.vaadinlab.contact.entity.Contact;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.function.SerializableConsumer;

public class ContactDialog extends Dialog {
    private final SerializableConsumer<Contact> onSaveCallback;
    private final ContactForm form;

    public ContactDialog(SerializableConsumer<Contact> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
        this.form = new ContactForm();
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
        setHeaderTitle("Give us some information about you");
        setWidth(40.0f, Unit.PERCENTAGE);
        add(form);
    }

    private void save() {
        var contact = form.getFormData();
        if (contact == null) {
            Notification.show("You have to complete all required fields");
            return;
        }
        onSaveCallback.accept(contact);
        close();
    }
}
