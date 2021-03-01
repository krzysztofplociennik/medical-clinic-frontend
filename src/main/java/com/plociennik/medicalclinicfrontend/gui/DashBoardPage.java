package com.plociennik.medicalclinicfrontend.gui;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class DashBoardPage extends VerticalLayout {

    public DashBoardPage() {

        setupText();
    }

    public void setupText() {
        H4 textWelcomeMessage = new H4("Welcome to our Clinic!");
        H5 textIntroMessage = new H5("Here you can manage your appointments and check which doctors are available!");

        add(textWelcomeMessage, textIntroMessage);
    }
}
