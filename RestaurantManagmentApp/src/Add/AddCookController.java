package Add;

import java.time.LocalDate;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Cook;
import javafx.event.ActionEvent;
import Utils.Expertise;
import Utils.Gender;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class AddCookController {

	@FXML
	private TextField txtFName;

	@FXML
	private TextField txtLName;

	@FXML
	private DatePicker date;

	@FXML
	private ComboBox<Gender> gender;

	@FXML
	private ComboBox<Expertise> expertise;

	@FXML
	private RadioButton isChefYes;

	@FXML
	private RadioButton isChefNo;

	@FXML
	private ToggleGroup chefTG;

	@FXML
	private Label message;

	//Initiate the page with current date and fill the combo-boxes with proper options
	public void initData() {
		// TODO Auto-generated method stub
		Utils.Utils.initDate(date);
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Expertise e : Expertise.values())
			expertise.getItems().add(e);
	}
	
	@FXML     //save cook to the restaurant
	public void save(ActionEvent e) {
		sounds.clickSound();
		Gender gend = gender.getSelectionModel().getSelectedItem();
		Expertise expert = expertise.getSelectionModel().getSelectedItem();
		boolean chef = false;
		if (isChefYes.isSelected())
			chef = true;
		else if (isChefNo.isSelected())
			chef = false;
		LocalDate bday = date.getValue();

		try {      			//validates the are no empty fields
			if (txtFName.getText() == null || txtFName.getText().isEmpty() || txtLName.getText() == null
					|| txtLName.getText().isEmpty() || gend == null || expert == null
					|| chefTG.getSelectedToggle() == null) {
				message.setText("you have fields that are empty");
				message.setTextFill(Color.RED);
			} else if (bday == null) {
				message.setText("Date must be mm/dd/yyyy");
				message.setTextFill(Color.RED);
			} else {  //if add succeeds ,clear all fields for further adding
				Cook cook = new Cook(txtFName.getText(), txtLName.getText(), bday, gend, expert, chef);
				if (Main.restaurant.addCook(cook)) {
					message.setText("saved succesfully");
					message.setTextFill(Color.GREEN);
					Main.changeHaveBeenMade = true;
					chefTG.getSelectedToggle().setSelected(false);
					txtLName.clear();
					txtFName.clear();
					gender.getSelectionModel().clearSelection();
					expertise.getSelectionModel().clearSelection();
					Utils.Utils.initDate(date);
				} else
					throw new CantAddObjectException("Cook " + cook.getFirstName() + " " + cook.getLastName());
			}
		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}

	}

}
