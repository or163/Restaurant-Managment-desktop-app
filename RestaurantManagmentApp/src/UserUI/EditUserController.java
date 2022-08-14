package UserUI;

import java.io.File;
import java.io.IOException;

import Audio.sounds;
import Model.Customer;
import Utils.Gender;
import Utils.Neighberhood;
import Utils.Utils;
import application.LoginController;
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

public class EditUserController {

	@FXML
	private Button saveButton;

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
	private ComboBox<Gender> gender;

	@FXML
	private PasswordField passw;
	
	@FXML
	private TextField showPassw;

	@FXML
	private Label message;

	@FXML
	private ComboBox<Neighberhood> neighborhood;

	@FXML
	private ImageView profilePic;

	private String imageUrl; //holds the url given in choose file, in order to finally set as user picture path in save method
	private ImageView image;  //pointer to top left profile image at user ui

	Customer cust = LoginController.getCustomer();

	
	// Initiates page with relevant user data, ready to set
	public void initData() {
		showPassw.setVisible(false);
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
		if (cust.getProfilePicturePath() != null) {
			profilePic.setImage(new Image(cust.getProfilePicturePath()));
			profilePic.setPreserveRatio(false);
		}
	}

	@FXML  //save edited data of user
	void save(ActionEvent event) {
		sounds.clickSound();
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
			if (imageUrl != null) {
				cust.setProfilePicturePath(imageUrl);  //set customer profile picture
				image.setImage(new Image(imageUrl)); //set profile picture in user controller ui top left
				image.setPreserveRatio(false);
			}
			message.setTextFill(Color.GREEN);
			message.setText("saved succefully");
			Main.changeHaveBeenMade = true;
		}
	}

	@FXML  //show or hide password on mouse enter and exit icon area
	private void showHidePassword(MouseEvent event) {
		Utils.showHidePassword(passw, showPassw);
	}
	
	@FXML  //upload profile picture
	public void chooseFile(ActionEvent event) throws IOException {
		sounds.clickSound();
		FileChooser fc = new FileChooser();
		File tmp = fc.showOpenDialog(Main.stage);
		if (tmp != null) {
			Image img = new Image("file:///" + tmp.getAbsolutePath());
			imageUrl = "file:///" + tmp.getAbsolutePath();
			profilePic.setImage(img);
			profilePic.setPreserveRatio(false);
		}
	}
	

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

}