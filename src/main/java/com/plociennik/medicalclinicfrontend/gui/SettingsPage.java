package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;

@Route("settings")
public class SettingsPage extends VerticalLayout {
    private Button logoutButton = new Button("logout");
    private Button saveMailButton = new Button("save");
    private Button cancelMailButton = new Button("cancel");
    private EmailField emailField = new EmailField();
    private Icon editMail = new Icon(VaadinIcon.EDIT);

    public SettingsPage() {
        saveMailButton.setVisible(false);
        cancelMailButton.setVisible(false);
        editMail.addClickListener(event -> {
            String oldMail = emailField.getValue();
            emailField.setReadOnly(false);
            saveMailButton.setVisible(true);
            cancelMailButton.setVisible(true);
            saveMailButton.addClickListener(saveEvent -> {
                Notification.show("The email has been changed!");
                emailField.setValue(emailField.getValue());
                saveMailButton.setVisible(false);
                cancelMailButton.setVisible(false);
                emailField.setReadOnly(true);
            });
            cancelMailButton.addClickListener(cancelEvent -> {
                saveMailButton.setVisible(false);
                cancelMailButton.setVisible(false);
                emailField.setValue(oldMail);
                emailField.setReadOnly(true);
            });
        });
        emailField.setLabel("Your current mail");
        emailField.setValue("example@com.pl");
        emailField.setReadOnly(true);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(emailField, editMail, saveMailButton, cancelMailButton);
        add(horizontalLayout, logoutButton);
    }
}
