package com.ctorres.petadoptionui.contact.ui;

import com.ctorres.petadoptionui.contact.entity.Contact;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import static com.ctorres.petadoptionui.contact.ContactMessages.EMAIL_REGEX_PATTERN;

public class ContactForm extends FormLayout {
    private final TextField fullNameField = new TextField();
    private final IntegerField ageField = new IntegerField();
    private final EmailField emailField = new EmailField();
    private final TextField phoneField = new TextField();
    private final TextArea addressField = new TextArea();
    private final TextArea reasonToAdoptField = new TextArea();

    public ContactForm() {
        configureForm();
        configureFields();
        addFields();
    }

    private void configureForm() {
        setWidthFull();
        setResponsiveSteps(new ResponsiveStep("0", 1));
    }

    private void configureFields() {
        fullNameField.setWidthFull();
        ageField.setWidthFull();
        emailField.setWidthFull();
        phoneField.setWidthFull();
        addressField.setWidthFull();
        reasonToAdoptField.setWidthFull();

        fullNameField.setPlaceholder("Carlos Torres");
        ageField.setPlaceholder("1");
        emailField.setPlaceholder("test@test.com");
        phoneField.setPlaceholder("809-179-8982");
        addressField.setPlaceholder("1400 United Street, Brooklyn");
        reasonToAdoptField.setPlaceholder("Write reasons about why you want to adopt it...");

        fullNameField.setRequired(true);
        ageField.setRequired(true);
        emailField.setRequired(true);
        phoneField.setRequired(true);
        addressField.setRequired(true);
        reasonToAdoptField.setRequired(true);
    }

    private void clearErrors() {
        fullNameField.getStyle().set("border", "");
        ageField.getStyle().set("border", "");
        emailField.getStyle().set("border", "");
        phoneField.getStyle().set("border", "");
        addressField.getStyle().set("border", "");
        reasonToAdoptField.getStyle().set("border", "");
    }

    private void addFields() {
        addFormItem(fullNameField, "Full Name");
        addFormItem(ageField, "Age");
        addFormItem(emailField, "Email");
        addFormItem(phoneField, "Phone number");
        addFormItem(addressField, "Address");
        addFormItem(reasonToAdoptField, "Reasons to adopt");
    }

    private boolean validate() {
        clearErrors();
        boolean result = true;
        if (fullNameField.isRequired() && fullNameField.getValue().isBlank()) {
            fullNameField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (ageField.isRequired() && (ageField.getValue() == null || ageField.getValue() <= 0)) {
            ageField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (emailField.isRequired() &&
                !emailField.getValue().matches(EMAIL_REGEX_PATTERN)) {
            emailField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (phoneField.isRequired() && !(phoneField.getValue().length() == 10)) {
            phoneField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (addressField.isRequired() && addressField.getValue().isBlank()) {
            addressField.getStyle().set("border", "1px solid red");
            result = false;
        }

        if (reasonToAdoptField.isRequired() && reasonToAdoptField.getValue().isBlank()) {
            reasonToAdoptField.getStyle().set("border", "1px solid red");
            result = false;
        }

        return result;
    }

    public Contact getFormData() {

        if (!validate()) {
            return null;
        }

        return new Contact(
                fullNameField.getValue(),
                ageField.getValue(),
                emailField.getValue(),
                phoneField.getValue(),
                addressField.getValue(),
                reasonToAdoptField.getValue()
        );
    }
}
