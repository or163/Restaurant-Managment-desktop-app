package Edit;

import java.time.LocalDate;

import Audio.sounds;
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

public class EditDPController {

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
    
    @FXML
    private ComboBox<DeliveryPerson> WhichDP;

  //Fills up the page with current data according to the selected Delivery person
    @FXML
    void DPselected(ActionEvent event) {
    	sounds.clickSound();
    	gender.getItems().clear();
    	vehicle.getItems().clear();
    	da.getItems().clear();
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Vehicle n : Vehicle.values())
			vehicle.getItems().add(n);
    	for (DeliveryArea g : Main.restaurant.getAreas().values())
    		da.getItems().add(g);
    	DeliveryPerson theSelected = WhichDP.getSelectionModel().getSelectedItem();
	    txtFName.setText(theSelected.getFirstName());
	    txtLName.setText(theSelected.getLastName());
	    date.setValue(theSelected.getBirthDay());
	    gender.setValue(theSelected.getGender());
	    vehicle.setValue(theSelected.getVehicle());
	    da.setValue(theSelected.getArea());
    }

    
    @FXML //save delivery person to the restaurant
	public void save(ActionEvent e) {
    	sounds.clickSound();
		Gender gend = gender.getSelectionModel().getSelectedItem();
		Vehicle car = vehicle.getSelectionModel().getSelectedItem();
		DeliveryArea d = da.getSelectionModel().getSelectedItem();
		LocalDate bday = date.getValue();
		DeliveryPerson theSelected = WhichDP.getSelectionModel().getSelectedItem();
		if (txtFName.getText() == null || txtFName.getText().isEmpty() || txtLName.getText() == null || txtLName.getText().isEmpty() ||
				gend == null || car == null || d == null || bday == null) {
			message.setTextFill(Color.RED);
			message.setText("you have fields that are empty");
		} else {
			theSelected.setArea(d);
			theSelected.setBirthDay(bday);
			theSelected.setFirstName(txtFName.getText());
			theSelected.setLastName(txtLName.getText());
			theSelected.setGender(gend);
			theSelected.setVehicle(car);
			message.setTextFill(Color.GREEN);
			message.setText("saved succesfully");
			Main.changeHaveBeenMade = true;
		}
	}

    //Initiate page
	public void initData() {
		// TODO Auto-generated method stub
		WhichDP.getItems().addAll(Main.restaurant.getDeliveryPersons().values());
	}
}
