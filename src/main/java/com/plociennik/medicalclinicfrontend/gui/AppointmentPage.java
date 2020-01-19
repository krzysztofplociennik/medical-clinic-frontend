package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("appointment")
//@UIScope
public class AppointmentPage extends VerticalLayout {
    private Button makeAppointmentButton = new Button("make one!");

    public AppointmentPage() {
        add(makeAppointmentButton);
    }
}
