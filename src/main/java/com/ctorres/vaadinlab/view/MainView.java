package com.ctorres.vaadinlab.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView() {

        Input input = new Input();

        add(new Text("Test"));
        add(input);
        add(new Button("Test me", buttonClickEvent -> {
            Notification.show(input.getValue());
        }));
    }

}
