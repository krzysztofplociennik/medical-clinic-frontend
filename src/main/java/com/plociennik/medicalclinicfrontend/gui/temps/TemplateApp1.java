package com.plociennik.medicalclinicfrontend.gui.temps;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route("app1")
public class TemplateApp1 extends AppLayout {
    public TemplateApp1() {
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        Tabs tabs = new Tabs(new Tab("Home"), new Tab("About"));
        img.setHeight("44px");
        addToNavbar(img, tabs);
    }
}
