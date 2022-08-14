package Add;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class AddCustomerToBlackListController {

	@FXML
	private ListView<Customer> customerLV;

	@FXML
	private Label message;
	
	 @FXML
	 private ImageView custPicture;

	// Initiate the page and fill list view in all customers
	public void initData() {
		customerLV.getItems().clear();
		for (Customer c : Main.restaurant.getCustomers().values()) {
			customerLV.getItems().add(c);
		}
		customerLV.getItems().removeAll(Main.restaurant.getBlackList()); // remove irrelevant customers such as customers already in the blacklist
	}

	@FXML // add selected customer to blacklist
	void AddToBList(ActionEvent event) {
		sounds.clickSound();
		Customer c = null;
		c = customerLV.getSelectionModel().getSelectedItem();

		if (customerLV == null)
			message.setText("There are no customers to add");
		else if (customerLV.getSelectionModel().getSelectedItem() == null)
			message.setText("Please Select a customer to add");
		else if (c != null) {

			Alert alert = new Alert(AlertType.CONFIRMATION); // make sure you want to add customer to blacklist
			alert.setTitle("Confirmation");
			alert.setHeaderText(customerLV.getSelectionModel().getSelectedItem() + " has been chosen");
			alert.setContentText("Are you sure you want to add this one?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Main.restaurant.addCustomerToBlackList(c);
				initData();
				message.setTextFill(Color.GREEN);
				message.setText("Added successfully");
				Main.changeHaveBeenMade = true;

			}
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