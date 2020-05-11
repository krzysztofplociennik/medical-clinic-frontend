package com.plociennik.medicalclinicfrontend.gui.temps;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import org.springframework.stereotype.Component;

@Component
public class PageHome extends VerticalLayout {
    private Text textRandom = new Text("Home");

    public PageHome() {
        add(textRandom);
    }

}
