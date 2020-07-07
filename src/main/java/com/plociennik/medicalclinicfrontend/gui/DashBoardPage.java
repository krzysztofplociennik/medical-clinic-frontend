package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DashBoardPage extends VerticalLayout {
    private Text textWelcomeMessage = new Text("Welcome to our Clinic!");
    private Text textIntroMessage = new Text("Here you can view your appointments, create a new one " +
            "or cancel if you have a need for it.");

    public DashBoardPage() {
        add(new Paragraph(textWelcomeMessage), textIntroMessage);
    }


}
