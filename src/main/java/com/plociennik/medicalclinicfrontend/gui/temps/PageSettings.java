package com.plociennik.medicalclinicfrontend.gui.temps;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PageSettings extends VerticalLayout {
    private Text textRandom = new Text("Settings");

    public PageSettings() {
        add(textRandom);
    }
}
