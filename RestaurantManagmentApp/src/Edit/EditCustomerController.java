package Edit;

import java.io.File;
import java.io.IOException;

import Audio.sounds;
import Model.Customer;
import Utils.Gender;
import Utils.Neighberhood;
import Utils.Utils;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class EditCustomerController {

	@FXML
	private RadioButton lactoseYes;

	@FXML
	private ToggleGroup lactoseTG;

	@FXML
	private RadioButton lactoseNo;

	@FXML
	private RadioButton glutenYes;

	@FXML
	private ToggleGroup glutenTG;

	@FXML
	private RadioButton glutenNo;

	@FXML
	private TextField userName;

	@FXML
	private TextField txtFName;

	@FXML
	private TextField txtLName;

	@FXML
	private DatePicker date;

	@FXML
	private PasswordField passw;
	
	@FXML
	private TextField showPassw;

	@FXML
	private Label message;

	@FXML
	private ComboBox<Neighberhood> neighborhood;

	@FXML
	private Button saveButton;

	@FXML
	private ComboBox<Gender> gender;

	@FXML
	private ComboBox<Customer> WhichCustomer;

	@FXML
	private ImageView profilePic;

	private String imageUrl;

	public void initData() {
		// TODO Auto-generated method stub
		showPassw.setVisible(false);
		WhichCustomer.getItems().addAll(Main.restaurant.getCustomers().values());
	}

	//Fills up the page with current data according to the selected customer
	@FXML
	void CustomerSelected(ActionEvent event) {
		Customer cust = WhichCustomer.getSelectionModel().getSelectedItem();
		gender.getItems().clear();
		neighborhood.getItems().clear();
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Neighberhood n : Neighberhood.values())
			neighborhood.getItems().add(n);
		userName.setText(cust.getUserName());
		passw.setText(cust.getPassword());
		txtFName.setText(cust.getFirstName());
		txtLName.setText(cust.getLastName());
		date.setValue(cust.getBirthDay());
		gender.setValue(cust.getGender());
		neighborhood.setValue(cust.getNeighberhood());
		if (cust.getIsSensitiveToLactose())
			lactoseTG.selectToggle(lactoseYes);
		else
			lactoseTG.selectToggle(lactoseNo);
		if (cust.getIsSensitiveToGluten())
			glutenTG.selectToggle(glutenYes);
		else
			glutenTG.selectToggle(glutenNo);
		if(cust.getProfilePicturePath() != null) {
			profilePic.setImage(new Image(cust.getProfilePicturePath()));
			profilePic.setPreserveRatio(false);
		}
		else
			profilePic.setImage(new Image("/Icons/addProfileImage.png"));

	}
	
	//Saves the updated data or informs if there is any problem with the info
	@FXML
	void save(ActionEvent event) {
		sounds.clickSound();
		Customer cust = WhichCustomer.getSelectionModel().getSelectedItem();
		if (userName.getText() == null || userName.getText().isEmpty() || txtFName.getText() == null
				|| txtFName.getText().isEmpty() || txtLName.getText() == null || txtLName.getText().isEmpty()
				|| lactoseTG.getSelectedToggle() == null || glutenTG.getSelectedToggle() == null) {
			message.setText("you have fields that are empty");
			message.setTextFill(Color.RED);
		} else if (Utils.isValidPassword(passw.getText(), message) == false)
			;
		else {
			cust.setUserName(userName.getText());
			cust.setPassword(passw.getText());
			cust.setFirstName(txtFName.getText());
			cust.setLastName(txtLName.getText());
			cust.setBirthDay(date.getValue());
			cust.setGender(gender.getSelectionModel().getSelectedItem());
			cust.setNeighberhood(neighborhood.getSelectionModel().getSelectedItem());
			if (lactoseYes.isSelected())
				cust.setSensitiveToLactose(true);
			else
				cust.setSensitiveToLactose(false);
			if (glutenYes.isSelected())
				cust.setSensitiveToGluten(true);
			else
				cust.setSensitiveToGluten(false);
			if (imageUrl != null)
				cust.setProfilePicturePath(imageUrl);
			message.setTextFill(Color.GREEN);
			message.setText("saved succefully");
			Main.changeHaveBeenMade = true;
		}
	}
	
	@FXML  //show or hide password on mouse enter and exit icon area
	private void showHidePassword(MouseEvent event) {
		Utils.showHidePassword(passw, showPassw);
	}

	@FXML  // edit profile picture
	public void chooseFile(ActionEvent event) throws IOException {
		sounds.clickSound();
		if (WhichCustomer.getSelectionModel().getSelectedItem() != null) {
			FileChooser fc = new FileChooser();
			File tmp = fc.showOpenDialog(Main.stage);
			if (tmp != null) {
				Image img = new Image("file:///" + tmp.getAbsolutePath());
				imageUrl = "file:///" + tmp.getAbsolutePath();
				profilePic.setImage(img);
				profilePic.setPreserveRatio(false);
			}
		}
		else
			message.setText("Please select a customer");
		
	}

}

