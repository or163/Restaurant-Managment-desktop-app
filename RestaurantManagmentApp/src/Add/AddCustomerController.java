package Add;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import Audio.sounds;
import Exceptions.CantAddObjectException;
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

public class AddCustomerController {

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
	private ComboBox<Neighberhood> neighborhood;

	@FXML
	private PasswordField passw;
	
	@FXML
	private TextField showPassw;

	@FXML
	private Label message;

	@FXML
	private ImageView profilePicture;  //Holder for customer profile picture, has a default value at SceneBuilder

	private String imageUrl;  //if customer adds image, would pupolate in customer's image url

	//Initiate the page with current date and fill the combo-boxes with proper options
	public void initData() {
		showPassw.setVisible(false);
		Utils.initDate(date);
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Neighberhood n : Neighberhood.values())
			neighborhood.getItems().add(n);
	}

	@FXML     //save customer to the restaurant
	public void save(ActionEvent e) {
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
		try {				//validates the are no empty fields
			if (userName.getText() == null || userName.getText().isEmpty() || txtFName.getText() == null
					|| txtFName.getText().isEmpty() || txtLName.getText() == null || txtLName.getText().isEmpty()
					|| gend == null || neigh == null || bday == null || lactoseTG.getSelectedToggle() == null
					|| glutenTG.getSelectedToggle() == null) {
				message.setTextFill(Color.RED);
				message.setText("you have fields that are empty");
			} else if (Utils.isValidPassword(passw.getText(), message) == false)
				; //if password is not legit
			else if (Utils.userNameExists(userName.getText())) {  //user name already exists
				message.setTextFill(Color.RED);
				message.setText("User already exists, choose different user name");
			}
			else {  //if add succeeds than clear all fields for further adding
				Customer cust = new Customer(txtFName.getText(), txtLName.getText(), bday, gend, neigh, lact, glut,
						userName.getText(), passw.getText());
				if (imageUrl != null)  //set customer profile picture through url, imageUrl get it's value if we choose an image to upload
					cust.setProfilePicturePath(imageUrl);
				if (Main.restaurant.addCustomer(cust)) {  //if add succeeds ,clear all fields for further adding
					message.setTextFill(Color.GREEN);
					message.setText("saved succesfully");
					Main.changeHaveBeenMade = true;
					lactoseTG.getSelectedToggle().setSelected(false);
					glutenTG.getSelectedToggle().setSelected(false);
					userName.clear();
					passw.clear();
					txtLName.clear();
					txtFName.clear();
					gender.getSelectionModel().clearSelection();
					neighborhood.getSelectionModel().clearSelection();
					Utils.initDate(date);
				} else
					throw new CantAddObjectException("Customer " + cust.getFirstName() + " " + cust.getLastName());
			}
		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}
	}
	
	@FXML  //show or hide password on mouse enter and exit icon area
	private void showHidePassword(MouseEvent event) {
		Utils.showHidePassword(passw, showPassw);
	}

	@FXML  //upload an image to be customer profile picture
	public void chooseFile(ActionEvent event) throws IOException {
		sounds.clickSound();
		FileChooser fc = new FileChooser();
		File tmp = fc.showOpenDialog(Main.stage);
		if (tmp != null) {
			Image img = new Image("file:///" + tmp.getAbsolutePath());
			this.imageUrl = "file:///" + tmp.getAbsolutePath();
			profilePicture.setImage(img);
			profilePicture.setPreserveRatio(false);
		}
	}

}
