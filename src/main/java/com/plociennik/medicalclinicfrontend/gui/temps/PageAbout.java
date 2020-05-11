package com.plociennik.medicalclinicfrontend.gui.temps;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PageAbout extends VerticalLayout {
    private Text textRandom = new Text("About");

    public PageAbout() {
        add(textRandom, textRandom, textRandom);
    }
}
