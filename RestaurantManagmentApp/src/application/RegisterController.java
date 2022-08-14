package application;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import Audio.sounds;
import Model.Customer;
import UserUI.UserController;
import Utils.Gender;
import Utils.Neighberhood;
import Utils.SerializableWiz;
import Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import javafx.stage.FileChooser;

public class RegisterController {

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
	private ChoiceBox<Gender> gender;

	@FXML
	private PasswordField passw;
	
	@FXML
	private TextField showPassw;

	@FXML
	private Label message;

	@FXML
	private ImageView profileImage;

	@FXML
	private ComboBox<Neighberhood> neighborhood;

	private String profilePath;

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	// Initiate combo-boxes with proper data and date
	public void initData() {
		showPassw.setVisible(false);
		Utils.initDate(date);
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Neighberhood n : Neighberhood.values())
			neighborhood.getItems().add(n);
	}

	@FXML  // save new user into system
	private void save(ActionEvent event) {
		sounds.clickSound();
		Gender gend = gender.getSelectionModel().getSelectedItem();
		Neighberhood neigh = neighborhood.getSelectionModel().getSelectedItem();
		boolean lact = false;
		boolean glut = false;
		if (lactoseYes.isSelected())
			lact = true;
		if (glutenYes.isSelected())
			glut = true;
		LocalDate bday = date.getValue();

		if (userName.getText() == null || userName.getText().isEmpty() || txtFName.getText() == null
				|| txtFName.getText().isEmpty() || txtLName.getText() == null || txtLName.getText().isEmpty()
				|| gend == null || neigh == null || bday == null || lactoseTG.getSelectedToggle() == null
				|| glutenTG.getSelectedToggle() == null) {  //validates all fields have been filled
			message.setText("you have fields that are empty");
		} else if (Utils.isValidPassword(passw.getText(), message) == false)  //validates password is in right format
			;
		else if (Utils.userNameExists(userName.getText()))  //validates user name doesn't already exist
			message.setText("User already exists, choose different user name");
		else {  //all data is legit, create new user
			Customer cust = new Customer(txtFName.getText(), txtLName.getText(), bday, gend, neigh, lact, glut,
					userName.getText(), passw.getText());
			if (profilePath != null)
				cust.setProfilePicturePath(profilePath);
			Main.restaurant.addCustomer(cust);
			try {
				SerializableWiz.save(Main.restaurant);
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
			message.setText("saved succesfully");
			try {  //start user ui scene
				FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/User.fxml"));
				Parent p;
				p = fx.load();
				UserController ctrl = (UserController) fx.getController();
				LoginController.setCustomer(cust);
				ctrl.initData();
				Scene s = new Scene(p, 700, 500);
				Main.stage.setScene(s);
				sounds.welcomeSound();
				sounds.backgroundMusic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML  //return to main login scene
	private void goHome(ActionEvent event) {
		try {
			sounds.clickSound();
			FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
			Parent p = fx.load();
			Scene s = new Scene(p, 700, 500);
			Main.stage.setScene(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML  //show or hide password on mouse enter and exit icon area
	private void showHidePassword(MouseEvent event) {
		Utils.showHidePassword(passw, showPassw);
	}
	
	@FXML  //select photo to upload as profile picture and also save the profile path for further using in the project
	public void chooseFile(ActionEvent event) {
		sounds.clickSound();
		FileChooser fc = new FileChooser();
		File tmp = fc.showOpenDialog(Main.stage);
		if (tmp != null) {
			Image img = new Image("file:///" + tmp.getAbsolutePath());
			String imageUrl = "file:///" + tmp.getAbsolutePath();
			profileImage.setImage(img);
			this.profilePath = imageUrl;
			profileImage.setPreserveRatio(false);
		}
	}

}
