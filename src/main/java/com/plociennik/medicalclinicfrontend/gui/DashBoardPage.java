package com.plociennik.medicalclinicfrontend.gui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DashBoardPage extends VerticalLayout {
    private Text welcomeMessage = new Text("Welcome to our Clinic!");

    public DashBoardPage() {
        Text lorem = new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris efficitur ipsum quis sapien maximus, " +
                "eu pellentesque nibh blandit. Praesent magna tellus, accumsan id lacinia quis, venenatis et felis. " +
                "Ut sagittis malesuada purus sit amet lacinia. Maecenas vel dui at elit suscipit aliquam non quis tellus. " +
                "Aenean ornare lorem odio, sit amet sagittis velit faucibus vitae. Morbi vestibulum dui augue, a vulputate tortor egestas quis. " +
                "Nullam sit amet hendrerit metus. Nullam laoreet, turpis sed semper interdum, dolor nunc faucibus massa, " +
                "a aliquet eros mauris in nibh. Praesent sem justo, viverra vitae erat id, congue accumsan elit. " +
                "Ut iaculis mauris eget ex hendrerit dignissim.\n" +
                "\n" +
                "Pellentesque feugiat malesuada dui, ac congue justo maximus sed. Donec imperdiet mi at nisi maximus, vitae ornare est dapibus. " +
                "Phasellus nec tincidunt massa. Sed lobortis nibh at est faucibus, ut rhoncus lacus dictum. Donec fringilla placerat semper. " +
                "Nam quis felis posuere, feugiat urna nec, iaculis neque. Nunc dui ligula, tempus at lacus euismod, varius malesuada lectus. " +
                "Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus sodales imperdiet leo ac fringilla. " +
                "Aliquam nec eros vitae massa porta imperdiet vitae porta tortor. Nulla varius, dolor suscipit auctor rhoncus, " +
                "mi neque laoreet nisi, ac congue eros quam malesuada ex. Sed eu pretium mi. Donec ac luctus tortor. " +
                "Curabitur pellentesque massa dolor, eget euismod lacus maximus semper. Mauris at lacinia sapien, at consequat dui. " +
                "Nulla ultricies massa ut risus rutrum, eget malesuada risus convallis.");
        add(welcomeMessage, lorem);

    }
}
