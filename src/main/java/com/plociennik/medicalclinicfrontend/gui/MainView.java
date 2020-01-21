package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Component
@Route
@UIScope
public class MainView extends VerticalLayout {
    private final DoctorsPage doctorsPage;
    private SettingsPage settingsPage = new SettingsPage();
    private AppointmentPage appointmentPage = new AppointmentPage();
    private HorizontalLayout mainHorizontal = new HorizontalLayout();
    private Tab appointmentTab = new Tab("Make an appointment");
    private Tab doctorsTab = new Tab("Our doctors");
    private Tab settingsTab = new Tab("Settings");
    private Map<Tab, Component> tabsToPages = new HashMap<>();
    private Tabs tabs = new Tabs(appointmentTab, doctorsTab, settingsTab);
    private Div pages;
    private Image logo = new Image("https://imgur.com/DPlKR0y.jpg", "logo");

    @Autowired
    public MainView (DoctorsPage doctorsPage) {
        this.doctorsPage = doctorsPage;
        this.pages = new Div(appointmentPage, doctorsPage, settingsPage);

        logo.setHeight("44px");
        appointmentPage.setVisible(false);
        doctorsPage.setVisible(false);
        settingsPage.setVisible(false);

        tabsToPages.put(appointmentTab, appointmentPage);
        tabsToPages.put(doctorsTab, doctorsPage);
        tabsToPages.put(settingsTab, settingsPage);
        Set<Component> pagesShown = Stream.of(appointmentPage)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        add(mainHorizontal, logo, tabs, pages);
    }
}

