package com.ctorres.vaadinlab.animal;

public class AnimalMessages {
    public static final String SCREEN_TITLE = "Animals for adoption";
    public static final String DETAILS_TITLE = "Animal details";
    public static final String ERROR_TITLE = "Oops! Something bad occurred :(";
    public static final String ERROR_LOADING_ANIMALS = "Failed to load animals. Please check your connection and try again.";
    public static final String ERROR_COMPLETING_FORM_FIELDS = "You have to complete all required fields";
    public static final String NO_ANIMALS = "No animals to adopt";

    public static String errorSavingAnimal(String name) {
        return "Error while trying to save animal: " + name;
    }

    public static String errorEditingAnimal(String name) {
        return "Error while trying to edit animal: " + name;
    }

    public static String setAdoptButtonText(String name) {
        return "Adopt " + name + "! :)";
    }

    public static String setAnimalPhotoAlt(String name) {
        return name + " animal photo";
    }
}
