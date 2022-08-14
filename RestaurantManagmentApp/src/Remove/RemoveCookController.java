package Remove;

import java.util.Optional;

import Audio.sounds;
import Model.Cook;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RemoveCookController {

	@FXML
	private ListView<Cook> cookLV;

	@FXML
	private Label message;
	
	@FXML
	private TextField id;

	// Initiate List View with all cooks inside
	public void initData() {

		cookLV.getItems().clear();
		for (Cook c : Main.restaurant.getCooks().values()) {
			cookLV.getItems().add(c);
		}
	}

		@FXML  // filter cook by id
		private void getCook(ActionEvent event) {
			sounds.clickSound();
			message.setText("");
			if (!Utils.Utils.isOnlyDigits(id.getText())) {  //validates that only digits are enterd to the text field
				message.setText("Wrong value! enter only numbers");
				message.setTextFill(Color.RED);
				return;
			}
			Cook cook = Main.restaurant.getRealCook(Integer.parseInt(id.getText()));
			cookLV.getSelectionModel().select(cook);
		}
	
	// this Method removes the selected cook from the restaurant
	@FXML
	private void remove(ActionEvent event) {
		sounds.clickSound();
		Cook c = null;
		c = cookLV.getSelectionModel().getSelectedItem();

		if (cookLV.getItems().size() == 0) { //in case there are no cooks in the list
			message.setText("There are no cooks to remove");
			message.setTextFill(Color.RED);
		}
		else if (cookLV.getSelectionModel().getSelectedItem() == null) { //no cook selected
			message.setText("Please Select a cook to Remove");
			message.setTextFill(Color.RED);
		}
		else if (c != null) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION); //if returned ok from alert remove cook
			alert.setTitle("Confirmation");
			alert.setHeaderText(cookLV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to delete this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.removeCook(c);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Removed successfully");
				Main.changeHaveBeenMade = true;

			} // else { // user chose CANCEL or closed the dialog
		}

	}

	@FXML
	void DeleteMessage(MouseEvent event) {
		message.setText(null);
	}

}
