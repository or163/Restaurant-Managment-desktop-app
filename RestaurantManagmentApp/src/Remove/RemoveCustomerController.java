package Remove;

import java.util.Optional;

import Audio.sounds;
import Model.Customer;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RemoveCustomerController {

	@FXML
	private ListView<Customer> customerLV;

	@FXML
	private Label message;

	@FXML
	private ImageView custPicture;
	
	@FXML
	private TextField id;

	// Initiate List View with all customers inside
	public void initData() {

		customerLV.getItems().clear();
		for (Customer c : Main.restaurant.getCustomers().values()) {
			customerLV.getItems().add(c);
		}
	}
	
	@FXML  // filter customer by id
	private void getCustomer(ActionEvent event) {
		sounds.clickSound();
		message.setText("");
		if (!Utils.Utils.isOnlyDigits(id.getText())) {  //validates that only digits are enterd to the text field
			message.setText("Wrong value! enter only numbers");
			message.setTextFill(Color.RED);
			return;
		}
		Customer cust = Main.restaurant.getRealCustomer(Integer.parseInt(id.getText()));
		customerLV.getSelectionModel().select(cust);
		custPicture.setPreserveRatio(false);
		if (cust != null) {
			if (cust.getProfilePicturePath() != null) { // check if picture exists
				custPicture.setImage(new Image(cust.getProfilePicturePath()));
			} else
				custPicture.setImage(new Image("/Icons/no_image_64px.png"));
		}
	}

	// this Method removes the selected customer from the restaurant
	@FXML
	private void remove(ActionEvent event) {
		sounds.clickSound();
		Customer c = null;
		c = customerLV.getSelectionModel().getSelectedItem();

		if (customerLV.getItems().size() == 0) { // in case there are no customers in the list
			message.setText("There are no customers to remove");
			message.setTextFill(Color.RED);
		}
		else if (customerLV.getSelectionModel().getSelectedItem() == null) { // no customer selected
			message.setText("Please Select a customer to Remove");
			message.setTextFill(Color.RED);
		}
		else if (c != null) {

			Alert alert = new Alert(AlertType.CONFIRMATION); // if returned ok from alert remove customer
			alert.setTitle("Confirmation");
			alert.setHeaderText(customerLV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to delete this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.removeCustomer(c);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Removed successfully");
				Main.changeHaveBeenMade = true;

			} // else { // user chose CANCEL or closed the dialog
		}

	}

	@FXML  // show customer profile picture (if exists)
	private void showCustomerImage() {
		if (customerLV.getSelectionModel().getSelectedItem() != null) {
			Customer c = (Customer) customerLV.getSelectionModel().getSelectedItem();
			custPicture.setPreserveRatio(false);
			if (c.getProfilePicturePath() != null) {  // check if picture exists
				custPicture.setImage(new Image(c.getProfilePicturePath()));
			} else
				custPicture.setImage(new Image("/Icons/no_image_64px.png"));
		}

	}

}
