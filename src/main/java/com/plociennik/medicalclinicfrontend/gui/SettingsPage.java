package com.plociennik.medicalclinicfrontend.gui;

import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.PatientDto;
import com.plociennik.medicalclinicfrontend.logic.SessionManager;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.GeneratedVaadinTextField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public SettingsPage(ApiClient apiClient, SessionManager sessionManager) {
        this.apiClient = apiClient;
        this.sessionManager = sessionManager;

        setSizeFull();

        setupCredentialsFields();
    }

    public void setupCredentialsFields() {
        GeneratedVaadinTextField nameField = new TextField("Current name", sessionManager.getLoggedInUserAsPatient().getName()),
                mailField = new EmailField("Current mail", sessionManager.getLoggedInUserAsPatient().getMail()),
                phoneNumberField = new TextField("Current phone number", sessionManager.getLoggedInUserAsPatient().getPhoneNumber()),
                usernameField = new TextField("Current username", sessionManager.getLoggedInUserAsPatient().getUsername()),
                passwordField = new PasswordField("Current password", sessionManager.getLoggedInUserAsPatient().getPassword());
        nameField.addClassName("setname");
        mailField.addClassName("setmail");
        phoneNumberField.addClassName("setphonenumber");
        usernameField.addClassName("setusername");
        passwordField.addClassName("setpassword");


        List<GeneratedVaadinTextField> listOfFields = Arrays.asList(nameField, mailField, phoneNumberField, usernameField, passwordField);

        Icon showIcon = new Icon(VaadinIcon.EYE);
        showIcon.setSize("30px");
        showIcon.addClickListener(event -> showOrHideAllFieldsContent(nameField, listOfFields));
        HorizontalLayout showContentLayout = new HorizontalLayout(showIcon, new Text("   - if you want to show/hide contents click the eye icon"));
        add(showContentLayout);

        for (GeneratedVaadinTextField field : listOfFields) {
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setAlignItems(Alignment.STRETCH);
            field.setReadOnly(true);
            Icon editIcon = new Icon(VaadinIcon.EDIT);
            Button saveChangeButton = new Button("save"), cancelChangeButton = new Button("cancel");
            String oldValue = field.getValue().toString();
            saveChangeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancelChangeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            saveChangeButton.setVisible(false);
            cancelChangeButton.setVisible(false);
            clickEvents(editIcon, saveChangeButton, cancelChangeButton, field, oldValue);

            horizontalLayout.add(field, editIcon, saveChangeButton, cancelChangeButton);
            add(horizontalLayout);
        }
    }

    public void clickEvents(Icon editIcon, Button saveButton, Button cancelButton, GeneratedVaadinTextField field, String oldValue) {
        editIcon.addClickListener(event -> {
            if (saveButton.isVisible()) {
                field.setReadOnly(true);
                field.setValue(oldValue);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
            } else {
                field.setReadOnly(false);
                saveButton.setVisible(true);
                cancelButton.setVisible(true);
            }
        });
        saveButton.addClickListener(event -> {
            try {
                changeFieldValue(field);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setReadOnly(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
        });
        cancelButton.addClickListener(event -> {
            field.setReadOnly(true);
            field.setValue(oldValue);
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

    public void showOrHideAllFieldsContent(GeneratedVaadinTextField nameField, List<GeneratedVaadinTextField> list) {
        if (nameField.isReadOnly()) {
            list.stream().forEach(generatedVaadinTextField -> generatedVaadinTextField.setReadOnly(false));
        } else {
            list.stream().forEach(generatedVaadinTextField -> generatedVaadinTextField.setReadOnly(true));
        }
    }
}
