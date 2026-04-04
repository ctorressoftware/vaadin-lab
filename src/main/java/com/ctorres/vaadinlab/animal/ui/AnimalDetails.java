package com.ctorres.vaadinlab.animal.ui;

import static com.ctorres.vaadinlab.animal.AnimalMessages.*;

import com.ctorres.vaadinlab.animal.entity.Animal;
import com.ctorres.vaadinlab.animal.AnimalService;
import com.ctorres.vaadinlab.common.Routes;
import com.ctorres.vaadinlab.contact.ContactMessages;

import static com.ctorres.vaadinlab.common.UIConstants.*;

import com.ctorres.vaadinlab.contact.ContactService;
import com.ctorres.vaadinlab.contact.ui.ContactDialog;
import com.ctorres.vaadinlab.common.ui.ResultModal;
import com.ctorres.vaadinlab.exception.AnimalNotFoundException;
import com.ctorres.vaadinlab.exception.MissedUUIDParameterException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.UUID;

@Route(value = Routes.DETAILS_PAGE)
public class AnimalDetails extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {
    private Animal animal;
    private final AnimalService animalService;
    private final ContactService contactService;
    private final H2 title = new H2(DETAILS_TITLE);
    private final Button backButton = new Button("Back");
    private final Button adoptButton = new Button();

    public AnimalDetails(AnimalService animalService, ContactService contactService) {
        this.animalService = animalService;
        this.contactService = contactService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String uuid) {
        if (uuid == null) throw new MissedUUIDParameterException();
        this.animal = animalService.findAnimalById(UUID.fromString(uuid))
                .orElseThrow(() -> new AnimalNotFoundException(uuid));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        removeAll();
        showTitleAndBackButton();
        showAnimalInformation();
        showAdoptButton();
    }

    private void showTitleAndBackButton() {
        backButton.addClickListener(event -> UI.getCurrent().navigate(Routes.DEFAULT_PAGE));
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
        adoptButton.setText(setAdoptButtonText(animal.getName()));
        adoptButton.addClickListener(event -> createContactDialog().open());
        add(adoptButton);
    }

    private ContactDialog createContactDialog() {
        return new ContactDialog(contact -> {
            try {
                contactService.save(contact);
                createResultDialog(
                        ContactMessages.SUCCESS_TITLE, ContactMessages.SUCCESS_MESSAGE, SUCCESS_MODAL_WIDTH).open();
            } catch (RuntimeException ex) {
                createResultDialog(
                        ContactMessages.ERROR_TITLE,
                        ContactMessages.errorSavingContact(contact.getFullName()),
                        ERROR_MODAL_WIDTH
                ).open();
            }
        });
    }

    private ResultModal createResultDialog(String title, String result, float width) {
        return new ResultModal(title, result, width);
    }

    private Image setPhotoDimensions(String url) {
        var image = new Image(url, setAnimalPhotoAlt(animal.getName()));
        image.setWidth(ANIMAL_PHOTO_WIDTH, Unit.PERCENTAGE);
        return image;
    }
}
