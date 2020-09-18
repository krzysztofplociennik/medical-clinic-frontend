package com.plociennik.medicalclinicfrontend.gui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Paragraph;
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
        Text textWelcomeMessage = new Text("Welcome to our Clinic!");
        Text textIntroMessage = new Text("Here you can manage your appointments and check which doctors" +
                " are available!");

        add(new Paragraph(textWelcomeMessage), textIntroMessage);
    }


}
