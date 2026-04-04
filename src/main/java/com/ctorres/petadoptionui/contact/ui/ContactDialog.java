package com.ctorres.petadoptionui.contact.ui;

import com.ctorres.petadoptionui.contact.entity.Contact;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.function.SerializableConsumer;

import static com.ctorres.petadoptionui.contact.ContactMessages.*;

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
        setHeaderTitle(CONTACT_FORM_TITLE);
        setWidth(40.0f, Unit.PERCENTAGE);
        add(form);
    }

    private void save() {
        var contact = form.getFormData();
        if (contact == null) {
            Notification.show(ERROR_COMPLETING_FORM_FIELDS);
            return;
        }
        onSaveCallback.accept(contact);
        close();
    }
}
