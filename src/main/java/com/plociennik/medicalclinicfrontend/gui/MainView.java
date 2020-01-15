package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("test")
public class MainView extends VerticalLayout {
    private DoctorsTab doctorsTabClass = new DoctorsTab(this);
    private HorizontalLayout mainHorizontal = new HorizontalLayout();
    private Tab appointmentTab = new Tab("Zarezerwuj wizytę");
    private Div appointmentPage = new Div();
    private Tab doctorsTab = new Tab("Nasi lekarze");
    private Div doctorsPage = new Div();
    private Tab settingsTab = new Tab("Ustawienia");
    private Div settingsPage = new Div();
    private Map<Tab, Component> tabsToPages = new HashMap<>();
    private Tabs tabs = new Tabs(appointmentTab, doctorsTab, settingsTab);
    private Div pages = new Div(appointmentPage, doctorsTabClass, settingsPage);
    private Text text = new Text("some");
    private Text text2 = new Text("some2");
    private Text text3 = new Text("some3");
    private Text text4 = new Text("Main horizontal layout");
    private Text text5 = new Text("some5");
    private Text text6 = new Text("some5");
    private Text text7 = new Text("some5");
    private Button button1 = new Button("1");
    private Button button2 = new Button("2");
    private Button button3 = new Button("3");
    private Image logo = new Image("https://imgur.com/DPlKR0y.jpg", "logo");

    public MainView() {
        logo.setHeight("44px");
        appointmentPage.setText("Page#1");
        doctorsPage.setText("Page#2");
        doctorsPage.setVisible(false);
        settingsPage.setText("Page#3");
        settingsPage.setVisible(false);
        doctorsTabClass.setVisible(false);

        tabsToPages.put(appointmentTab, appointmentPage);
        //tabsToPages.put(doctorsTab, doctorsPage);
        tabsToPages.put(doctorsTab, doctorsTabClass);
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
        //tabs.setWidthFull();
        //pages.setWidthFull();
        HorizontalLayout appLay = new HorizontalLayout();
        HorizontalLayout docLay = new HorizontalLayout();
        VerticalLayout setLay = new VerticalLayout();
        appLay.add(text, text5, button1);
        docLay.add(text2, text6, button2);
        setLay.add(text3, text7, button3);
        appointmentPage.add(appLay);
        doctorsPage.add(docLay);
        settingsPage.add(setLay);
        System.out.println(logo.getElement().getPropertyNames());
        mainHorizontal.add(text4);
        add(mainHorizontal, logo, tabs, pages);
    }
}

