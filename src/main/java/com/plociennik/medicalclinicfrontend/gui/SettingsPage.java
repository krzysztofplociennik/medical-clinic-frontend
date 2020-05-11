package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;

public class SettingsPage extends VerticalLayout {
    private Button logoutButton = new Button("logout");
    private Button saveMailButton = new Button("save");
    private Button cancelMailButton = new Button("cancel");
    private Button savePasswordButton = new Button("save");
    private Button cancelPasswordButton = new Button("cancel");
    private EmailField emailField = new EmailField();
    private PasswordField passwordField = new PasswordField();
    private Icon editMail = new Icon(VaadinIcon.EDIT);
    private Icon editPassword = new Icon(VaadinIcon.EDIT);

    public SettingsPage() {
        saveMailButton.setVisible(false);
        saveMailButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cancelMailButton.setVisible(false);
        cancelMailButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        savePasswordButton.setVisible(false);
        savePasswordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cancelPasswordButton.setVisible(false);
        cancelPasswordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        editMail.addClickListener(event -> {
            String oldMail = emailField.getValue();
            emailField.setReadOnly(false);
            saveMailButton.setVisible(true);
            cancelMailButton.setVisible(true);
            saveMailButton.addClickListener(saveEvent -> {
                if(emailField.isInvalid()) {
                    Notification.show("The mail you gave is invalid!");
                } else { Notification.show("The mail has been changed!"); }
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
        editPassword.addClickListener(event -> {
            String oldPass = passwordField.getValue();
            passwordField.setReadOnly(false);
            savePasswordButton.setVisible(true);
            cancelPasswordButton.setVisible(true);
            savePasswordButton.addClickListener(saveEvent -> {
                Notification.show("The password has been changed!");
                passwordField.setValue(passwordField.getValue());
                savePasswordButton.setVisible(false);
                cancelPasswordButton.setVisible(false);
                passwordField.setReadOnly(true);
            });
            cancelPasswordButton.addClickListener(cancelEvent -> {
                savePasswordButton.setVisible(false);
                cancelPasswordButton.setVisible(false);
                passwordField.setValue(oldPass);
                passwordField.setReadOnly(true);
            });
        });
        emailField.setLabel("Your current mail");
        emailField.setValue("example@com.pl");
        emailField.setReadOnly(true);
        passwordField.setLabel("Your current password");
        passwordField.setValue("jan123");
        passwordField.setReadOnly(true);
        HorizontalLayout mailLayout = new HorizontalLayout();
        HorizontalLayout passwordLayout = new HorizontalLayout();
        mailLayout.add(emailField, editMail, saveMailButton, cancelMailButton);
        passwordLayout.add(passwordField, editPassword, savePasswordButton, cancelPasswordButton);
        add(mailLayout, passwordLayout, logoutButton);
    }
}
