package com.plociennik.medicalclinicfrontend.gui.temps;
import com.plociennik.medicalclinicfrontend.client.ApiClient;
import com.plociennik.medicalclinicfrontend.domain.ReservationDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Route("gridView")
@Component
@UIScope
public class TemplateGridView extends VerticalLayout {
    private Button button = new Button("button");
    private Text text = new Text("text");
    private ApiClient apiClient;
    private Grid<ReservationDto> listOfReservationsGrid = new Grid<>(ReservationDto.class);

    public TemplateGridView(ApiClient apiClient) {
        this.apiClient = apiClient;
        listOfReservationsGrid.setItems(apiClient.getReservations());
        setSizeFull();
        add(listOfReservationsGrid, text, button);
    }
}
