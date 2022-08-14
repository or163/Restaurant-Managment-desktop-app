package Remove;

import java.util.Optional;

import Audio.sounds;
import Model.Component;
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

public class RemoveComponentController {

	@FXML
	private ListView<Component> compLV;

	@FXML
	private Label message;

	@FXML
	private TextField id;

	// Initiate List View with all components inside
	public void initData() {

		compLV.getItems().clear();
		for (Component c : Main.restaurant.getComponenets().values()) {
			compLV.getItems().add(c);
		}
	}

	@FXML  // filter component by id
	private void getComponent(ActionEvent event) {
		sounds.clickSound();
		message.setText("");
		if (!Utils.Utils.isOnlyDigits(id.getText())) {  //validates that only digits are enterd to the text field
			message.setText("Wrong value! enter only numbers");
			message.setTextFill(Color.RED);
			return;
		}
		Component comp = Main.restaurant.getRealComponent(Integer.parseInt(id.getText()));
		compLV.getSelectionModel().select(comp);
	}

	// this Method removes the selected component from the restaurant
	@FXML
	private void remove(ActionEvent event) {
		sounds.clickSound();
		Component c = null;
		c = compLV.getSelectionModel().getSelectedItem();

		if (compLV.getItems().size() == 0) {   //in case there are no components in the list
			message.setText("There are no components to remove");
			message.setTextFill(Color.RED);
		}
		else if (compLV.getSelectionModel().getSelectedItem() == null) { //no component selected
			message.setText("Please Select a component to Remove");
			message.setTextFill(Color.RED);
		}
		else if (c != null) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION); //if returned ok from alert remove component
			alert.setTitle("Confirmation");
			alert.setHeaderText(compLV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to delete this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.removeComponent(c);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Removed successfully");
				Main.changeHaveBeenMade = true;

			} // else { // user chose CANCEL or closed the dialog
		
		}

	}
}
