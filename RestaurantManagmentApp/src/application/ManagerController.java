package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import Add.AddController;
import Audio.sounds;
import Edit.EditController;
import Model.Delivery;
import Model.Dish;
import Model.Order;
import Querries.QuerriesController;
import Remove.RemoveController;
import Utils.SerializableWiz;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;

public class ManagerController {

	private static int counter = 0;
	private static int counter2 = 0;
	
	@FXML
	BorderPane pannelRoot;
	
	@FXML
	private VBox vbox;

	@FXML
	private AnchorPane anchor;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private TableView<Delivery> deliveriesTV;
	
	@FXML
	private TableColumn<Delivery, Integer> delId;

	@FXML
	private TableColumn<Delivery, String> dp;

	@FXML
	private TableColumn<Delivery, String> da;

	@FXML
	private TableColumn<Delivery, LocalDate> date;
	
	@FXML
	private TableView<Order> ordersTV;

	@FXML
	private TableColumn<Order, Integer> ordId;

	@FXML
	private TableColumn<Order, String> cust;

	@FXML
	private TableColumn<Order, Dish> dishes;

	@FXML
	private TableColumn<Order, String> delivery;
	
	@FXML
    private ImageView Audio;
	
	@FXML
    private MediaView mv;
	

	// Initiate table views of uncompleted deliveries and orders that not added yet to deliveries 
	public void initData() {
		//prepare table view to delivery fields
		delId.setCellValueFactory(new PropertyValueFactory<>("id"));  
		dp.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(
				c.getValue().getDeliveryPerson().getFirstName() +  " " + c.getValue().getDeliveryPerson().getLastName())));
		da.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getArea().getAreaName())));
		date.setCellValueFactory(new PropertyValueFactory<>("deliveredDate"));
		for (Delivery d : Main.restaurant.getDeliveries().values()) {  //Populate deliveries TV with uncompleted deliveries
			if (!d.isDelivered())
				deliveriesTV.getItems().add(d);
		}
		//prepare tavle view to order fields
		ordId.setCellValueFactory(new PropertyValueFactory<>("id"));  
		cust.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(
				c.getValue().getCustomer().getFirstName() +  " " + c.getValue().getCustomer().getLastName())));
		dishes.setCellValueFactory(new PropertyValueFactory<>("dishes"));
		delivery.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getDelivery())));
		for (Order o : Main.restaurant.getOrders().values()) {  //Populate orders TV with orders that has no delivery
			if (o.getDelivery() == null)
				ordersTV.getItems().add(o);
		}
	}
	
	@FXML  //Initiate and go to home page
	void goHome(ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Manager.fxml"));
		Parent p = fx.load();
		ManagerController ctrl = (ManagerController) fx.getController();
		ctrl.initData();
		Scene s = new Scene(p, 700, 500);
		Main.stage.setScene(s);
	}
	
	@FXML  //Initiate and go to login page
    void GoLogin(ActionEvent event) throws IOException {
		sounds.backgroundMusicMute();
		sounds.bellSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
		Parent p = fx.load();
		Scene s = new Scene(p, 700, 500);
		Main.stage.setScene(s);
    }
	
	@FXML  //Initiate and go to data bases page
	void goDatabases (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/ViewDataBases.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		ViewDatabasesController ctrl = (ViewDatabasesController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	
	@FXML  //Initiate and go to add page
	void goAdd (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Add.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddController ctrl = (AddController) fx.getController();
		ctrl.setPannelRoot(pannelRoot);
		pannelRoot.setCenter(pp);
	}

	@FXML  //Initiate and go to remove page
	void goRemove (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Remove.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveController ctrl = (RemoveController) fx.getController();
		ctrl.setPannelRoot(pannelRoot);
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //Initiate and go to edit page
	void goEdit (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Edit.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditController ctrl = (EditController) fx.getController();
		ctrl.setPannelRoot(pannelRoot);
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //Initiate and go to querries page
	void goQuerries (ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/Querries.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		QuerriesController ctrl = (QuerriesController) fx.getController();
		ctrl.setPannelRoot(pannelRoot);
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //exit program
	private void exitButtonAction(ActionEvent event){
		Utils.Utils.exitButtonAction();
	}
	
	@FXML  //show or hide menu according to counter, if even hide, else show
	void showMenu(MouseEvent event) {
		sounds.clickSound();
		if(ManagerController.counter % 2 == 0) {
			vbox.setVisible(false);
			anchor.setStyle("-fx-background-color: transparent");
		}
		else {
			vbox.setVisible(true);
			anchor.setStyle("-fx-background-color: darkblue");
			}
		ManagerController.counter++;
	}
	
	
	@FXML  //turn sound on and off according to counter, if even mute, else turn on
    void MuteOnOff(MouseEvent event) {
		sounds.clickSound();
		if(ManagerController.counter2  % 2 == 1)
			Audio.setImage(new Image("Icons/audio_64px.png"));
		else
			Audio.setImage(new Image("Icons/no_audio_64px.png"));
		counter2++;
		sounds.backgroundMusic();
    }
	
	@FXML  //save all current data and serialize it
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
