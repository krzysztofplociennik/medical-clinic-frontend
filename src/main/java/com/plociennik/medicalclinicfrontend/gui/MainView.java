package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
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
    private final AppointmentPage appointmentPage;
    private DashBoardPage dashBoardPage = new DashBoardPage();
    private SettingsPage settingsPage = new SettingsPage();
    private HorizontalLayout mainHorizontalLayout = new HorizontalLayout();
    private Tab dashBoardTab = new Tab("Dashboard");
    private Tab appointmentTab = new Tab("Appointments");
    private Tab doctorsTab = new Tab("Our doctors");
    private Tab settingsTab = new Tab("Settings");
    private Map<Tab, Component> tabsToPages = new HashMap<>();
    private Tabs tabs = new Tabs(dashBoardTab, appointmentTab, doctorsTab, settingsTab);
    private Div pages;
    private Image logo = new Image("https://imgur.com/DPlKR0y.jpg", "logo");
    private Button buttonLogout = new Button("log out");

    @Autowired
    public MainView (DoctorsPage doctorsPage, AppointmentPage appointmentPage) {
        this.doctorsPage = doctorsPage;
        this.appointmentPage = appointmentPage;
        this.pages = new Div(dashBoardPage, appointmentPage, doctorsPage, settingsPage);

        setupPages();
        setupTabs();
        setupLogout();
        setupLogo();

        mainHorizontalLayout.add(logo, buttonLogout);

        add(mainHorizontalLayout, tabs, pages);
        setSizeFull();
    }

    public void setupPages() {
        appointmentPage.setVisible(false);
        doctorsPage.setVisible(false);
        settingsPage.setVisible(false);
        pages.setSizeFull();
    }

    public void setupTabs() {
        tabsToPages.put(dashBoardTab, dashBoardPage);
        tabsToPages.put(appointmentTab, appointmentPage);
        tabsToPages.put(doctorsTab, doctorsPage);
        tabsToPages.put(settingsTab, settingsPage);
        Set<Component> pagesShown = Stream.of(dashBoardPage)
                .collect(Collectors.toSet());

        tabs.addThemeVariants(TabsVariant.LUMO_SMALL);

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
    }

    public void setupLogout() {
        buttonLogout.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLogout.setText("logout");
    }

    public void setupLogo() {
        logo.setHeight("100px");
        logo.setWidth("450");
    }
}

