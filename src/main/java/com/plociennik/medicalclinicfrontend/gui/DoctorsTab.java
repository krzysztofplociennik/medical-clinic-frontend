package com.plociennik.medicalclinicfrontend.gui;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DoctorsTab extends HorizontalLayout {
    private MainView mainView;
    private Text text1 = new Text("John Doe");
    private Button button1 = new Button("Rate him!");

    public DoctorsTab(MainView mainView) {
        this.mainView = mainView;
        add(text1, button1);
    }

}
