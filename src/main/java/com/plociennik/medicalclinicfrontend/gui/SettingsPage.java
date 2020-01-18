package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class SettingsPage extends VerticalLayout {
    private MainView mainView;
    private Button logoutButton = new Button("logout");

    public SettingsPage(MainView mainView) {
        this.mainView = mainView;
        add(logoutButton);
    }
}
