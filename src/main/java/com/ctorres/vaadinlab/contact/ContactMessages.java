package com.ctorres.vaadinlab.contact;

public class ContactMessages {
    public static final String SUCCESS_TITLE = "Contact created successfully!";
    public static final String SUCCESS_MESSAGE = """
            We registered your contact information successfully. 
            You will be contacted in the next 24 hours.""";

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
