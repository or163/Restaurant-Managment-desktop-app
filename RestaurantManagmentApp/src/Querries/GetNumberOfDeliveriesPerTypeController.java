package Querries;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class GetNumberOfDeliveriesPerTypeController {

	@FXML
	private ComboBox<String> ChosenType;
	
	@FXML
    private Label resultt;
	
	// Initiate combo-box with type of delivery
	public void initData() {
		ChosenType.getItems().addAll("Regular delivery","Express delivery");
		
		// TODO Auto-generated method stub
		
	}
	
	@FXML   // show number of deliveries according to selected type
	private void GetNumberOfDeliveriesPerType(ActionEvent event) {
		String chosen = ChosenType.getSelectionModel().getSelectedItem();
		int num= Main.restaurant.getNumberOfDeliveriesPerType().get(chosen);
		resultt.setText(String.valueOf(num));
		}
	
}
