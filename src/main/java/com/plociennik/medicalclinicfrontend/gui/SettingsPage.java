package com.plociennik.medicalclinicfrontend.gui;

import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.logic.SessionManager;
import com.plociennik.medicalclinicfrontend.security.SecurityConfiguration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.GeneratedVaadinTextField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
@UIScope
public class SettingsPage extends VerticalLayout {
    private ApiClient apiClient;
    private SessionManager sessionManager;
    private SecurityConfiguration securityConfiguration;

    @Autowired
    public SettingsPage(ApiClient apiClient, SessionManager sessionManager, SecurityConfiguration securityConfiguration) {
        this.apiClient = apiClient;
        this.sessionManager = sessionManager;
        this.securityConfiguration = securityConfiguration;

        setSizeFull();

        setupCredentialsFields();
    }

    public void setupCredentialsFields() {
        TextField nameField = new TextField("Current name", sessionManager.getLoggedInUserAsPatient().getName()),
                phoneNumberField = new TextField("Current phone number", sessionManager.getLoggedInUserAsPatient().getPhoneNumber()),
                usernameField = new TextField("Current username", sessionManager.getLoggedInUserAsPatient().getUsername());
        EmailField mailField = new EmailField("Current mail", sessionManager.getLoggedInUserAsPatient().getMail());
        PasswordField  passwordField = new PasswordField("Current password", sessionManager.getLoggedInUserAsPatient().getPassword());
        nameField.addClassName("setname");
        mailField.addClassName("setmail");
        phoneNumberField.addClassName("setphonenumber");
        usernameField.addClassName("setusername");
        passwordField.addClassName("setpassword");

        List<GeneratedVaadinTextField> listOfFields = Arrays.asList(nameField, mailField, phoneNumberField, usernameField, passwordField);

        H5 introMessage = new H5("If you want to show and edit a specific content click the edit icon");
        add(introMessage);

        for (GeneratedVaadinTextField field : listOfFields) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setAlignItems(Alignment.BASELINE);
            field.setReadOnly(true);
            Icon editIcon = new Icon(VaadinIcon.EDIT);
            Button saveChangeButton = new Button("save"), cancelChangeButton = new Button("cancel");
            if (field.getClassName().equals("setusername")) {
                editIcon = new Icon(VaadinIcon.EYE);
                saveChangeButton.setEnabled(false);
            }
            saveChangeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelChangeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            saveChangeButton.setVisible(false);
            cancelChangeButton.setVisible(false);
            editClickEvent(editIcon, saveChangeButton, cancelChangeButton, field);
            saveClickEvent(sessionManager.getLoggedInUserAsPatient(), saveChangeButton, cancelChangeButton, field);
            cancelClickEvent(saveChangeButton, cancelChangeButton, field);

            horizontalLayout.add(field, editIcon, saveChangeButton, cancelChangeButton);
            add(horizontalLayout);
        }
    }

    public void editClickEvent(Icon editIcon, Button saveButton, Button cancelButton, GeneratedVaadinTextField field) {
        editIcon.addClickListener(event -> {
            if (saveButton.isVisible()) {
                field.setReadOnly(true);
                try {
                    field.setValue(getFieldOldValue(field));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
            } else {
                field.setReadOnly(false);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
            }
        });
    }

    public void saveClickEvent(PatientDto patient, Button saveButton, Button cancelButton, GeneratedVaadinTextField field) {
        saveButton.addClickListener(event -> {
            if (field.getClassName().equals("setmail") && checkIfEmailIsInvalid((EmailField) field)) {
                try {
                    field.setValue(getFieldOldValue(field));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setReadOnly(false);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
                Notification.show("The mail is invalid!", 3000, Notification.Position.MIDDLE);
            } else {
                try {
                    changeFieldValue(field);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Notification.show("Successfully edited!", 3000, Notification.Position.BOTTOM_CENTER);
                field.setReadOnly(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                if (field.getClassName().equals("setpassword")) {
                    updatePassword(patient, field.getValue().toString());
                }
            }
        });
    }

    public void cancelClickEvent(Button saveButton, Button cancelButton, GeneratedVaadinTextField field) {
        cancelButton.addClickListener(event -> {
            try {
                field.setValue(getFieldOldValue(field));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setReadOnly(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
        });
    }

    public void changeFieldValue(GeneratedVaadinTextField editedField) throws IOException, InvocationTargetException, IllegalAccessException {
        PatientDto patientToBeEdited = apiClient.getPatients().stream()
                .filter(patientDto -> patientDto.getUsername().equals(sessionManager.getLoggedInUserAsPatient().getUsername()))
                .findAny().get();

        Method[] methods = patientToBeEdited.getClass().getMethods();
        Method searchedMethod = Arrays.stream(methods)
                .filter(method -> method.getName().toLowerCase().contains(editedField.getClassName()))
                .findFirst().get();
        searchedMethod.invoke(patientToBeEdited, editedField.getValue());

        apiClient.updatePatient(patientToBeEdited);
    }

    public String getFieldOldValue(GeneratedVaadinTextField field) throws InvocationTargetException, IllegalAccessException {
        StringBuilder toBeEditedMethodName = new StringBuilder(field.getClassName());
        toBeEditedMethodName.setCharAt(0, 'g');
        PatientDto loggedInPatient = sessionManager.getLoggedInUserAsPatient();
        Method[] methods = loggedInPatient.getClass().getMethods();
        Method searchedMethod = Arrays.stream(methods)
                .filter(method -> method.getName().toLowerCase().contains(toBeEditedMethodName))
                .findFirst().get();
        return (String) searchedMethod.invoke(loggedInPatient);
    }

    public boolean checkIfEmailIsInvalid(EmailField emailField) {
        return emailField.isInvalid();
    }

    public void updatePassword(PatientDto patient, String newPassword) {
        UserDetails userToUpdate = User.withDefaultPasswordEncoder()
                .username(patient.getUsername())
                .password(patient.getPassword())
                .roles("USER")
                .build();
        securityConfiguration.getMemoryUserDetailsManager().updatePassword(userToUpdate, "{noop}" + newPassword);
    }
}
