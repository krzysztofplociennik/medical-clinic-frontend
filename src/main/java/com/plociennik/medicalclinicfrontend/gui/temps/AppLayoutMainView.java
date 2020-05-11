package com.plociennik.medicalclinicfrontend.gui.temps;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "Clinic", shortName = "ClinicApp")
@org.springframework.stereotype.Component
@Route("appMain")
@UIScope
public class AppLayoutMainView extends AppLayout {
    private DrawerToggle drawerToggle = new DrawerToggle();
    private Text textRandom = new Text("Lorem ipsum");
    private PageHome pageHome = new PageHome();
    private PageAbout pageAbout = new PageAbout();
    private PageSettings pageSettings = new PageSettings();
    private Tab tabHome = new Tab("Home");
    private Tab tabAbout = new Tab("About");
    private Tab tabSettings = new Tab("Settings");

    private Map<Tab, Component> tabsToPages = new HashMap<>();
    private Tabs tabs = new Tabs(tabHome, tabAbout, tabSettings);
    private Div pages;

    @Autowired
    public AppLayoutMainView() {
        this.pages = new Div(pageHome, pageAbout, pageSettings);
        pageHome.setVisible(false);
        pageAbout.setVisible(false);
        pageSettings.setVisible(false);

        tabsToPages.put(tabHome, pageHome);
        tabsToPages.put(tabAbout, pageAbout);
        tabsToPages.put(tabSettings, pageSettings);

        Set<Component> pagesShown = Stream.of(pageAbout).collect(Collectors.toSet());

        tabs.setWidthFull();

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");
        addToNavbar(drawerToggle, img);
        Tabs tabs = new Tabs(tabHome, tabAbout, tabSettings);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs, pages);
    }
}
