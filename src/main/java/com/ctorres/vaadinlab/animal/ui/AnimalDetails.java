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
import com.ctorres.vaadinlab.exception.MissingUuidParameterException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
        addClassName("app-screen");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String uuid) {
        if (uuid == null) throw new MissingUuidParameterException();
        this.animal = animalService.findAnimalById(UUID.fromString(uuid))
                .orElseThrow(() -> new AnimalNotFoundException(uuid));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        removeAll();

        var wrapper = new VerticalLayout();
        wrapper.addClassName("details-wrapper");
        wrapper.setPadding(false);
        wrapper.setSpacing(true);

        wrapper.add(buildHeader(), buildAnimalInformationCard(), buildAdoptSection());

        add(wrapper);
    }

    private Component buildHeader() {
        backButton.addClickListener(event -> UI.getCurrent().navigate(Routes.DEFAULT_PAGE));

        title.addClassName("screen-title");
        backButton.addClassName("action-button");

        var header = new HorizontalLayout(title, backButton);
        header.addClassName("details-header");
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);

        return header;
    }

    private Component buildAnimalInformationCard() {
        var content = new VerticalLayout(
                createInfoRow("ID:", animal.getId().toString()),
                createInfoRow("Name:", animal.getName()),
                createInfoRow("Gender:", animal.getGender().name()),
                createInfoRow("Age:", String.valueOf(animal.getAge())),
                createInfoRow("Specie:", animal.getSpecie().name()),
                createInfoRow("Personality:", animal.getPersonality()),
                createPhotoSection()
        );

        content.setPadding(false);
        content.setSpacing(false);

        var card = new Div(content);
        card.addClassName("details-card");
        return card;
    }

    private Component buildAdoptSection() {
        adoptButton.setText(setAdoptButtonText(animal.getName()));
        adoptButton.addClassName("adopt-btn");
        adoptButton.addClickListener(event -> createContactDialog().open());
        return new Div(adoptButton);
    }

    private Component createInfoRow(String label, String value) {
        var labelParagraph = new Paragraph(label);
        labelParagraph.addClassName("info-label");

        var valueParagraph = new Paragraph(value);
        valueParagraph.addClassName("info-value");

        var row = new HorizontalLayout(labelParagraph, valueParagraph);
        row.addClassName("info-row");
        row.setWidthFull();
        row.setPadding(false);
        row.setSpacing(true);

        return row;
    }

    private Component createPhotoSection() {
        var label = new Paragraph("Photo:");
        label.addClassName("photo-label");

        var image = animal.getImage() == null
                ? new Paragraph("No image")
                : setPhotoDimensions(animal.getImage());

        var frame = new Div(image);
        frame.addClassName("photo-frame");

        var block = new VerticalLayout(label, frame);
        block.addClassName("photo-block");
        block.setPadding(false);
        block.setSpacing(false);

        return block;
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
        image.setWidth("320px");
        image.addClassName("animal-image");
        return image;
    }
}
