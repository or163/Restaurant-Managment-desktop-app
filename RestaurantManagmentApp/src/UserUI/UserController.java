package UserUI;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import Audio.sounds;
import Model.Customer;
import Utils.SerializableWiz;
import application.LoginController;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserController {

	@FXML
	private BorderPane pannelRoot; // pannelRoot is the main pannel of user UI and all other screens in user ui would display via pannelRoot

	@FXML
	private Label welcome;
	
	@FXML
    private Label TimeMessage;
	
	@FXML
	private VBox vbox;
	
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private ImageView Audio;
	
	@FXML
	private Button exitButton;
	
	@FXML
    private ImageView profilePic;
	
	private static int counter = 0; // counter for show menu method, if even hide menu, else show
	
	private static int counter2 = 0;
	
	public ImageView getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(ImageView profilePic) {
		this.profilePic = profilePic;
	}

	// Initiate page with welcome to current user and sets profile pic at top of the screen for the user, if picture exists
	public void initData() {
		Customer c = LoginController.getCustomer();
		welcome.setText("Welcome " + c.getFirstName());
		if(c.getProfilePicturePath() != null) {
			profilePic.setImage(new Image(c.getProfilePicturePath()));
			profilePic.setPreserveRatio(false);
		}
		else
			profilePic.setImage(new Image("/Icons/male_user_60px.png"));
		Calendar ca = Calendar.getInstance();
		int timeOfDay = ca.get(Calendar.HOUR_OF_DAY);
		TimeMessage.setWrapText(true);
		if(timeOfDay >= 0 && timeOfDay < 12){ //shows a message according to the current time
			TimeMessage.setText("Good Morning,"
					+ "\n enjoy your wonderful day");        
		}else if(timeOfDay >= 12 && timeOfDay < 16){
			TimeMessage.setText("Good Afternoon,\n"
					+ " we hope you are hungry");
		}else if(timeOfDay >= 16 && timeOfDay < 21){
			TimeMessage.setText("Good Evening,\n "
					+ "enjoy your wonderful evening");
		}else if(timeOfDay >= 21 && timeOfDay < 24){
			TimeMessage.setText("Good Night,\n "
					+ "are you up for a night snack");
		}
		else
			TimeMessage.setText("");
		TimeMessage.autosize();
		
		
	}
	
	@FXML  // go to main user ui screen
	void goHome(ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/User.fxml"));
		Parent p = fx.load();
		UserController ctrl = (UserController) fx.getController();
		ctrl.initData();
		Scene s = new Scene(p, 700, 500);
		Main.stage.setScene(s);
	}
	
	@FXML // log out and go to login page
    void GoLogin(ActionEvent event) throws IOException {
		sounds.backgroundMusicMute();
		sounds.bellSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
		Parent p = fx.load();
		Scene s = new Scene(p, 700, 500);
		Main.stage.setScene(s);
    }
	
	@FXML  // go to menu & make order page
	void goMakeOrder(ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/MakeOrder.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		MakeOrderController ctrl = (MakeOrderController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to edit personal details page
	private void goEdit(ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditUser.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditUserController ctrl = (EditUserController) fx.getController();
		ctrl.initData();
		ctrl.setImage(profilePic);  //make profilePic pointer at Edit page so when changing picture it will be update also in the side-bar
		pannelRoot.setCenter(pp);
	}
	
	@FXML   // go to shopping cart page
	void goShoppingCart (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/ShoppingCart.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		ShoppingCartController ctrl = (ShoppingCartController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to queries page
	void goQuerries (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/UserQuerries.fxml"));
		Pane p = fx.load();
		BorderPane bp = (BorderPane) p;
		UserQuerriesController ctrl = (UserQuerriesController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(bp);
	}
	
	@FXML  // go user's order history page
	void goHistory (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/UserHistory.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		UserHistoryController ctrl = (UserHistoryController) fx.getController();
		ctrl.initData();
		UserHistoryController.setBorder(pannelRoot);
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // show or hide menu according to counter, if even hide, else show
	void showMenu(MouseEvent event) {
		sounds.clickSound();
		if(UserController.counter % 2 == 0) {
			vbox.setVisible(false);
			anchor.setStyle("-fx-background-color: transparent");
		}
		else {
			vbox.setVisible(true);
			anchor.setStyle("-fx-background-color: #171717");
			}
		UserController.counter++;
	}
	
	@FXML  // exit program
	private void exitButtonAction(ActionEvent event){
		Utils.Utils.exitButtonAction();
	}
	
	@FXML  //Controls the background music and changes the icon accordingly 
    void MuteOnOff(MouseEvent event) {
		sounds.clickSound();
		if(UserController.counter2  % 2 == 1) {
			Audio.setImage(new Image("Icons/audio_64px.png"));
		}
		else
		{
			Audio.setImage(new Image("Icons/no_audio_64px.png"));
		}
		counter2++;
		sounds.backgroundMusic();
    }
	
	@FXML  // save all current data and serialize it
	void SaveToSerelizebaleFile(ActionEvent event) {
		sounds.clickSound();
		try {
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Save");
			a.setContentText("Are you sure you want to save?");
			Optional<ButtonType> result = a.showAndWait();
			if (result.get() == ButtonType.OK){
				SerializableWiz.save(Main.restaurant);
				Main.changeHaveBeenMade = false;
			} else
			    ;
		}catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

}