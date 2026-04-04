package com.ctorres.petadoptionui.contact;

public class ContactMessages {
    public static final String SUCCESS_TITLE = "Contact created successfully!";
    public static final String SUCCESS_MESSAGE = """
            We registered your contact information successfully. 
            You will be contacted in the next 24 hours.""";
    public static final String ERROR_COMPLETING_FORM_FIELDS = "You have to complete all required fields";
    public static final String CONTACT_FORM_TITLE = "Give us some information about you";
    public static final String EMAIL_REGEX_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static final String ERROR_TITLE = "Oops! Something bad occurred :(";
    public static String errorSavingContact(String fullName) {
        return "An error occurred while trying to save the user's contact: " + fullName;
    }

    public static String errorSavingAnimal(String name) {
        return "Error while trying to save animal: " + name;
    }

    public static String errorEditingAnimal(String name) {
        return "Error while trying to edit animal: " + name;
    }
}
