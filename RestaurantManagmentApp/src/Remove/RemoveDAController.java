package Remove;

import java.util.Optional;

import Audio.sounds;
import Model.DeliveryArea;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class RemoveDAController {

	@FXML
	private ListView<DeliveryArea> DALV;

	@FXML
	private ListView<DeliveryArea> DALV2;

	@FXML
	private Label message;

	public void initData() {
		DALV.getItems().clear();
		DALV2.getItems().clear();
		DALV.getSelectionModel().clearSelection();
		DALV.getItems().addAll(Main.restaurant.getAreas().values());
		DALV2.getSelectionModel().clearSelection();
		DALV2.getItems().addAll(Main.restaurant.getAreas().values());
	}

	@FXML  // this Method removes the selected delivery area from the restaurant
	void remove(ActionEvent event) {
		sounds.clickSound();
		DeliveryArea da = null;
		DeliveryArea da2 = null;
		da = DALV.getSelectionModel().getSelectedItem();
		da2 = DALV2.getSelectionModel().getSelectedItem();

		if (DALV.getItems().size() == 0) { //in case there are no delivery areas in the list
			message.setText("There are no areas to remove");
			message.setTextFill(Color.RED);
		}
		else if (DALV.getSelectionModel().getSelectedItem() == null
				|| DALV2.getSelectionModel().getSelectedItem() == null) {  //no delivery areas selected
			message.setText("Please Select areas");
			message.setTextFill(Color.RED);
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION); //if returned ok from alert remove delivery area
			alert.setTitle("Confirmation");
			alert.setHeaderText(DALV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to delete this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.removeDeliveryArea(da, da2);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Removed successfully");
				Main.changeHaveBeenMade = true;

			} // else { // user chose CANCEL or closed the dialog
		}
		initData();
	}
}
