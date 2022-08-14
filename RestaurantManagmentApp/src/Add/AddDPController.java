package Add;

import java.time.LocalDate;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Utils.Gender;
import Utils.Vehicle;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddDPController {

	@FXML
	private Button save;

	@FXML
	private TextField txtFName;

	@FXML
	private TextField txtLName;

	@FXML
	private DatePicker date;

	@FXML
	private ComboBox<Gender> gender;

	@FXML
	private ComboBox<Vehicle> vehicle;

	@FXML
	private ComboBox<DeliveryArea> da;

	@FXML
	private Label message;

	//Initiate the page with current date and fill the combo-boxes with proper options
	public void initData() {
		// TODO Auto-generated method stub
		Utils.Utils.initDate(date);
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Vehicle v : Vehicle.values())
			vehicle.getItems().add(v);
		for (DeliveryArea d : Main.restaurant.getAreas().values())
			da.getItems().add(d);
	}

	@FXML   //save delivery person to the restaurant
	public void save(ActionEvent e) {
		sounds.clickSound();
		Gender gend = gender.getSelectionModel().getSelectedItem();
		Vehicle car = vehicle.getSelectionModel().getSelectedItem();
		DeliveryArea d = da.getSelectionModel().getSelectedItem();
		LocalDate bday = date.getValue();

		try {				//validates the are no empty fields
			if (txtFName.getText() == null || txtFName.getText().isEmpty() || txtLName.getText() == null
					|| txtLName.getText().isEmpty() || gend == null || car == null || d == null || bday == null) {
				message.setTextFill(Color.RED);
				message.setText("you have fields that are empty");
			} else {  //if add succeeds ,clear all fields for further adding
				DeliveryPerson dp = new DeliveryPerson(txtFName.getText(), txtLName.getText(), bday, gend, car, d);
				if (Main.restaurant.addDeliveryPerson(dp, d)) {
					message.setText("saved succesfully");
					message.setTextFill(Color.GREEN);
					Main.changeHaveBeenMade = true;
					Utils.Utils.initDate(date);
					txtLName.clear();
					txtFName.clear();
					gender.getSelectionModel().clearSelection();
					vehicle.getSelectionModel().clearSelection();
					da.getSelectionModel().clearSelection();
				} else
					throw new CantAddObjectException("Delivery Person " + dp.getFirstName() + " " + dp.getLastName());
			}
		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}
	}

}
