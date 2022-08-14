package Remove;

import java.util.Optional;

import Audio.sounds;
import Model.Dish;
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

public class RemoveDishController {

	@FXML
	private ListView<Dish> dishLV;

	@FXML
	private Label message;
	
	@FXML
	private TextField id;

	// Initiate List View with all dishes inside
	public void initData() {

		dishLV.getItems().clear();
		for (Dish d : Main.restaurant.getDishes().values()) {
			dishLV.getItems().add(d);
		}
	}

	@FXML  // filter dish by id
	private void getDish(ActionEvent event) {
		sounds.clickSound();
		message.setText("");
		if (!Utils.Utils.isOnlyDigits(id.getText())) {  //validates that only digits are enterd to the text field
			message.setText("Wrong value! enter only numbers");
			message.setTextFill(Color.RED);
			return;
		}
		Dish dish = Main.restaurant.getRealDish(Integer.parseInt(id.getText()));
		dishLV.getSelectionModel().select(dish);
	}
	
	// this Method removes the selected dish from the restaurant
	@FXML
	private void remove(ActionEvent event) {
		sounds.clickSound();
		Dish d = null;
		d = dishLV.getSelectionModel().getSelectedItem();

		if (dishLV.getItems().size() == 0) { //in case there are no dishes in the list
			message.setText("There are no dishes to remove");
			message.setTextFill(Color.RED);
		}
		else if (dishLV.getSelectionModel().getSelectedItem() == null) { //no dish selected
			message.setText("Please Select a dish to Remove");
			message.setTextFill(Color.RED);
		}
		else if (d != null) {

			Alert alert = new Alert(AlertType.CONFIRMATION); //if returned ok from alert remove dish
			alert.setTitle("Confirmation");
			alert.setHeaderText(dishLV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to delete this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.removeDish(d);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Removed successfully");
				Main.changeHaveBeenMade = true;

			} // else { // user chose CANCEL or closed the dialog
		}

	}
}
