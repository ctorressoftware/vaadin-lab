package com.ctorres.vaadinlab.animal.ui;

import com.ctorres.vaadinlab.animal.entity.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.ctorres.vaadinlab.contact.ContactService;
import com.ctorres.vaadinlab.contact.ui.ContactDialog;
import com.ctorres.vaadinlab.common.ui.ResultModal;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.UUID;

@Route(value = "/details")
public class AnimalDetails extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {
    private Animal animal;
    private final AnimalService animalService;
    private final ContactService contactService;
    private final H2 title = new H2("Animal details");
    private final Button backButton = new Button("Back");
    private final Button adoptButton = new Button();

    public AnimalDetails(AnimalService animalService, ContactService contactService) {
        this.animalService = animalService;
        this.contactService = contactService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String uuid) {
        if (uuid == null) throw new RuntimeException("UUID parameter is required");
        this.animal = animalService.findAnimalById(UUID.fromString(uuid))
                .orElseThrow(() -> new RuntimeException("Animal doesn't exists. UUID =" + uuid));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        removeAll();
        showTitleAndBackButton();
        showAnimalInformation();
        showAdoptButton();
    }

    private void showTitleAndBackButton() {
        backButton.addClickListener(event -> UI.getCurrent().navigate("/"));
        add(title, backButton);
    }

    private <T extends Component> Div createContentFrame(String title, T data, boolean isVertical) {
        var header = new Paragraph(title);
        var content = isVertical ?
                new VerticalLayout(header, data) :
                new HorizontalLayout(header, data);
        return new Div(content);
    }

    private void showAnimalInformation() {
        var id = createContentFrame("ID:", new Paragraph(animal.getId().toString()), false);
        var name = createContentFrame("Name:", new Paragraph(animal.getName()), false);
        var gender = createContentFrame("Gender:", new Paragraph(animal.getGender().name()), false);
        var age = createContentFrame("Age:", new Paragraph(String.valueOf(animal.getAge())), false);
        var specie = createContentFrame("Specie:", new Paragraph(animal.getSpecie().name()), false);
        var personality = createContentFrame("Personality:", new Paragraph(animal.getPersonality()), false);
        var photo = createContentFrame("Photo:", animal.getImage() == null ? new Paragraph("No image") :
                        setPhotoDimensions(animal.getImage()),
                animal.getImage() != null);
        add(new VerticalLayout(id, name, gender, age, specie, personality, photo));
    }

    private void showAdoptButton() {
        adoptButton.setText("Adopt " + animal.getName() + "! :)");
        adoptButton.addClickListener(event -> createContactDialog().open());
        add(adoptButton);
    }

    private ContactDialog createContactDialog() {
        return new ContactDialog(contact -> {
            try {
                contactService.save(contact);
                createResultDialog("Contact created successfully!", """
                                We registered your contact information successfully.
                                You will be contacted in the next 24 hours.""",
                        40f
                ).open();
            } catch (RuntimeException ex) {
                createResultDialog("Oops! Something bad occurred :(",
                        "An error occurred while trying to save the user's contact: " + contact.getFullName(),
                        30f
                ).open();
            }
        });
    }

    private ResultModal createResultDialog(String title, String result, float width) {
        return new ResultModal(title, result, width);
    }

    private Image setPhotoDimensions(String url) {
        var image = new Image(url, animal.getName() + " animal photo");
        image.setWidth(30f, Unit.PERCENTAGE);
        return image;
    }
}
