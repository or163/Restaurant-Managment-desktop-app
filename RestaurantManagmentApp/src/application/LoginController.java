package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Audio.sounds;
import Model.Customer;
import UserUI.UserController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LoginController implements Initializable{
	
	//current user in system
	private static Customer customer;

	@FXML
	private Hyperlink register;

	@FXML
	private TextField user;

	@FXML
	private PasswordField passw;

	@FXML
	private Label message;
	
	@FXML
	private Button signIn;
	
	@FXML
    private Line userLine;

    @FXML
    private Line passwLine;
    
    @FXML
    private MediaView mv;
    private MediaPlayer mp;
	private Media me;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	String path = new File("src/Audio/Restaurant Ad Video2.mp4").getAbsolutePath();
    	me = new Media(new File(path).toURI().toString());
    	mp = new MediaPlayer(me);
    	mv.setMediaPlayer(mp);
    	mv.toBack();
    	mp.setAutoPlay(true);
    	mp.setVolume(0.05);
    	mp.setCycleCount(MediaPlayer.INDEFINITE);
    	DoubleProperty width = mv.fitWidthProperty();
    	DoubleProperty height = mv.fitHeightProperty();
    	width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
    	height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
	}
    
	
	@FXML
	void login(ActionEvent event) {
		String un = user.getText();
		String pass = passw.getText();
		if (un == null || un.isEmpty())
			message.setText("Username is empty!");
		if (pass == null || pass.isEmpty())
			message.setText("Password is empty!");

		// check user connection
		if (un.equals("manager")) {
			if (pass.equals("manager")) {
				// start manager scene
				try {
					FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Manager.fxml"));
					Parent p = fx.load();
					ManagerController ctrl = (ManagerController) fx.getController();
					ctrl.initData();
					Scene s = new Scene(p, 700, 500);
					Main.stage.setScene(s);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sounds.clickSound();
				mp.setMute(true);
				sounds.welcomeMSound();
				sounds.backgroundMusic();
			} else {  //is manager but wrong password
				userLine.setStroke(Color.LIGHTBLUE);
				passwLine.setStroke(Color.RED);
				message.setText("Incorrect Password");
				sounds.clickSound();
			}
		} else {  //the current user isn't the manager
			
			for (Customer c : Main.restaurant.getCustomers().values()) {
				if (c.getUserName().equals(un)) {
					if (c.getPassword().equals(pass)) { // if the password match the user name
						LoginController.customer = c;  //setting this user as current user at system, from now on we can reach him by LoginCOntroller.getCustomer from everywhere in the project
						try {
							FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/User.fxml"));
							Parent p = fx.load();
							UserController ctrl = (UserController) fx.getController();
							ctrl.initData();
							//sounds.clickSound();

							Scene s = new Scene(p, 700, 500);
							Main.stage.setScene(s);
							

						} catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
						mp.setMute(true);
						sounds.welcomeSound();
						sounds.backgroundMusic();
					} else {  //password didn't match
						passwLine.setStroke(Color.RED);
						userLine.setStroke(Color.LIGHTBLUE);
						message.setText("Incorrect Password");
						sounds.clickSound();
						return;
					}
				}
			}  //user name doesn't exist
			userLine.setStroke(Color.RED);
			passwLine.setStroke(Color.LIGHTBLUE);
			message.setText("User does not exist"); // user doesn't exist
			sounds.clickSound();
		}
	}

	@FXML
	public void signup(ActionEvent e) {
		sounds.clickSound();
		mp.setMute(true);
		try {  // start register scene
			FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Register.fxml"));
			Parent p = fx.load();
			RegisterController ctrl = (RegisterController) fx.getController();
			ctrl.initData();
			Scene s = new Scene(p, 700, 500);
			Main.stage.setScene(s);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static Customer getCustomer() {
		return customer;
	}

	public static void setCustomer(Customer customer) {
		LoginController.customer = customer;
	}

	

}
