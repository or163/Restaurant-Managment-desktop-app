package Remove;

import java.util.Optional;

import Audio.sounds;
import Model.Delivery;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class RemoveDeliveryController {

	@FXML
	private ListView<Delivery> dellLV;

	@FXML
	private Label message;
	
	@FXML
	private TextField id;

	// Initiate List View with all deliveries inside
	public void initData() {

		dellLV.getItems().clear();
		dellLV.getItems().addAll(Main.restaurant.getDeliveries().values());
	}
	
	@FXML  // filter delivery by id
	private void getDelivery(ActionEvent event) {
		sounds.clickSound();
		message.setText("");
		if (!Utils.Utils.isOnlyDigits(id.getText())) {  //validates that only digits are enterd to the text field
			message.setText("Wrong value! enter only numbers");
			message.setTextFill(Color.RED);
			return;
		}
		Delivery del = Main.restaurant.getRealDelivery(Integer.parseInt(id.getText()));
		dellLV.getSelectionModel().select(del);
	}

	// this Method removes the selected delivery from the restaurant
	@FXML
	private void remove(ActionEvent event) {
		sounds.clickSound();
		Delivery d = null;
		d = dellLV.getSelectionModel().getSelectedItem();

		if (dellLV.getItems().size() == 0) { //in case there are no deliveries in the list
			message.setText("There are no deliveries to remove");
			message.setTextFill(Color.RED);
		}
		else if (dellLV.getSelectionModel().getSelectedItem() == null) { //no delivery selected
			message.setText("Please Select a delivery to Remove");
			message.setTextFill(Color.RED);
		}
		else if (d != null) {

			Alert alert = new Alert(AlertType.CONFIRMATION); //if returned ok from alert remove delivery
			alert.setTitle("Confirmation");
			alert.dialogPaneProperty().get().setPrefWidth(600);
			alert.setHeaderText(dellLV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to delete this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.removeDelivery(d);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Removed successfully");
				Main.changeHaveBeenMade = true;

			} // else { // user chose CANCEL or closed the dialog
		}

	}
}