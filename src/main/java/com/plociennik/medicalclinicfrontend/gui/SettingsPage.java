package com.plociennik.medicalclinicfrontend.gui;

import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.logic.SessionManager;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class SettingsPage extends VerticalLayout {
    private ApiClient apiClient;
    private SessionManager sessionManager;
    private Button saveMailButton = new Button("save");
    private Button cancelMailButton = new Button("cancel");
    private Button savePasswordButton = new Button("save");
    private Button cancelPasswordButton = new Button("cancel");
    private EmailField emailField = new EmailField();
    private PasswordField passwordField = new PasswordField();
    private Icon editMail = new Icon(VaadinIcon.EDIT);
    private Icon editPassword = new Icon(VaadinIcon.EDIT);
    private HorizontalLayout mailLayout = new HorizontalLayout();
    private HorizontalLayout passwordLayout = new HorizontalLayout();

    @Autowired
    public SettingsPage(ApiClient apiClient, SessionManager sessionManager) {
        this.apiClient = apiClient;
        this.sessionManager = sessionManager;

        setSizeFull();

        setupEmailView();
        setupPasswordView();

        add(mailLayout, passwordLayout);
    }

    public void setupEmailView() {
        saveMailButton.setVisible(false);
        saveMailButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveMailButton.addClickShortcut(Key.ENTER);
        cancelMailButton.setVisible(false);
        cancelMailButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        saveMailButton.addClickShortcut(Key.ESCAPE);

        editMail.addClickListener(event -> {
            String oldMail = emailField.getValue();
            emailField.setReadOnly(false);
            saveMailButton.setVisible(true);
            cancelMailButton.setVisible(true);
            saveMailButton.addClickListener(saveEvent -> {
                if(emailField.isInvalid()) {
                    Notification.show("The mail you gave is invalid!");
                } else if (emailField.getValue().equals(oldMail)) {
                    saveMailButton.setVisible(false);
                    cancelMailButton.setVisible(false);
                    emailField.setReadOnly(true);
                } else {
                    Notification.show("The mail has been changed!");
                    emailField.setValue(emailField.getValue());
                    saveMailButton.setVisible(false);
                    cancelMailButton.setVisible(false);
                    emailField.setReadOnly(true);
                }
            });
            cancelMailButton.addClickListener(cancelEvent -> {
                saveMailButton.setVisible(false);
                cancelMailButton.setVisible(false);
                emailField.setValue(oldMail);
                emailField.setReadOnly(true);
            });
        });

        emailField.setLabel("Your current mail");
        emailField.setValue(sessionManager.getLoggedInUserAsPatient().getMail());
        emailField.setReadOnly(true);

        mailLayout.add(emailField, editMail, saveMailButton, cancelMailButton);
    }

    public void setupPasswordView() {
        savePasswordButton.setVisible(false);
        savePasswordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        savePasswordButton.addClickShortcut(Key.ENTER);
        cancelPasswordButton.setVisible(false);
        cancelPasswordButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelPasswordButton.addClickShortcut(Key.ESCAPE);

        editPassword.addClickListener(event -> {
            String oldPass = passwordField.getValue();
            passwordField.setReadOnly(false);
            savePasswordButton.setVisible(true);
            cancelPasswordButton.setVisible(true);
            savePasswordButton.addClickListener(saveEvent -> {
                if (passwordField.getValue().equals(oldPass)) {
                    savePasswordButton.setVisible(false);
                    cancelPasswordButton.setVisible(false);
                    passwordField.setReadOnly(true);
                } else {
                    Notification.show("The password has been changed!");
                    passwordField.setValue(passwordField.getValue());
                    savePasswordButton.setVisible(false);
                    cancelPasswordButton.setVisible(false);
                    passwordField.setReadOnly(true);
                }
            });
            cancelPasswordButton.addClickListener(cancelEvent -> {
                savePasswordButton.setVisible(false);
                cancelPasswordButton.setVisible(false);
                passwordField.setValue(oldPass);
                passwordField.setReadOnly(true);
            });
        });

        passwordField.setLabel("Your current password");
        passwordField.setValue(sessionManager.getLoggedInUserAsPatient().getPassword());
        passwordField.setReadOnly(true);

        passwordLayout.add(passwordField, editPassword, savePasswordButton, cancelPasswordButton);
    }
}
