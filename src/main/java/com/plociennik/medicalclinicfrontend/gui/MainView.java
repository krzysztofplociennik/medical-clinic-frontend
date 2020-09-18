package com.plociennik.medicalclinicfrontend.gui;

import com.plociennik.medicalclinicfrontend.logic.SessionManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Component
@Route
@PageTitle("Dashboard | Clinic")
@UIScope
public class MainView extends VerticalLayout {
    private final DoctorsPage doctorsPage;
    private final AppointmentPage appointmentPage;
    private DashBoardPage dashBoardPage;
    private SettingsPage settingsPage;
    private final SessionManager sessionManager;

    @Autowired
    public MainView (DashBoardPage dashBoardPage,
                     AppointmentPage appointmentPage,
                     DoctorsPage doctorsPage,
                     SettingsPage settingsPage,
                     SessionManager sessionManager) {
        this.dashBoardPage = dashBoardPage;
        this.doctorsPage = doctorsPage;
        this.appointmentPage = appointmentPage;
        this.settingsPage = settingsPage;
        this.sessionManager = sessionManager;

        setSizeFull();

        setupTopLayout();
        setupTabs();
        setupPages();
    }

    public void setupTopLayout() {
        HorizontalLayout topHorizontalLayout = new HorizontalLayout();
        topHorizontalLayout.setWidthFull();
        Image logo = new Image("https://imgur.com/DPlKR0y.jpg", "logo");

        HorizontalLayout adminModeLayout = new HorizontalLayout();
        adminModeLayout.setWidthFull();
        adminModeLayout.setJustifyContentMode(JustifyContentMode.END);
        Text adminModeText = new Text("ADMIN MODE");
        if (sessionManager.isAdmin()) {
            adminModeLayout.add(adminModeText);
        }

        HorizontalLayout logoutButtonLayout = new HorizontalLayout();
        logoutButtonLayout.setWidthFull();

        Anchor logout = new Anchor("/logout", "Log out");
        logoutButtonLayout.add(logout);
        logoutButtonLayout.setAlignItems(Alignment.START);
        logoutButtonLayout.setJustifyContentMode(JustifyContentMode.END);

        topHorizontalLayout.add(logo, adminModeLayout, logoutButtonLayout);
        add(topHorizontalLayout);
    }

    public void setupTabs() {
        Icon iconDashboard = new Icon(VaadinIcon.HOME);
        iconDashboard.setSize("38px");
        iconDashboard.setColor("0000F0");
        Tab dashBoardTab = new Tab(iconDashboard);

        Icon iconAppointments = new Icon(VaadinIcon.LIST_OL);
        iconAppointments.setSize("38px");
        iconAppointments.setColor("0000F0");
        Tab appointmentTab = new Tab(iconAppointments);

        Icon iconDoctors = new Icon(VaadinIcon.DOCTOR);
        iconDoctors.setSize("38px");
        iconDoctors.setColor("0000F0");
        Tab doctorsTab = new Tab(iconDoctors);

        Icon iconSettings = new Icon(VaadinIcon.COG);
        iconSettings.setSize("38px");
        iconSettings.setColor("0000F0");
        Tab settingsTab = new Tab(iconSettings);

        Tabs tabs = new Tabs(dashBoardTab, appointmentTab, doctorsTab, settingsTab);
        tabs.setHeight("200px");

        Map<Tab, Component> tabsToPages = new HashMap<>();
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
        add(tabs);
    }

    public void setupPages() {
        Div pages = new Div(dashBoardPage, appointmentPage, doctorsPage, settingsPage);
        pages.setSizeFull();

        dashBoardPage.setSizeFull();
        appointmentPage.setVisible(false);
        appointmentPage.setSizeFull();
        doctorsPage.setVisible(false);
        doctorsPage.setSizeFull();
        settingsPage.setVisible(false);
        settingsPage.setSizeFull();
        add(pages);
    }
}

