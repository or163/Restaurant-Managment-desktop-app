package UserUI;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

import Audio.sounds;
import Model.Customer;
import Model.Delivery;
import Model.Dish;
import Model.Order;
import application.LoginController;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UserHistoryController {

	@FXML
	private TableView<Order> ordersTV;

	@FXML
	private TableColumn<Order, Integer> id;

	@FXML
	private TableColumn<Order, Dish> dishes;

	@FXML
	private TableColumn<Order, Delivery> delivery;

	private static BorderPane border;  // in UserController when moving to this page sets the value of border to be the main border pane of the user controller aka: pannelRoot

	public static void setBorder(BorderPane border) {
		UserHistoryController.border = border;
	}

	// Initiate Table View of orders, sorted by id, dishes and delivery
	// the table view contains all relevant customer orders (if exists)
	public void initData() {
		ordersTV.setPlaceholder(new Label("There are no orders"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		dishes.setCellValueFactory(new PropertyValueFactory<>("dishes"));
		delivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
		HashMap<Customer, TreeSet<Order>> ordersByCustomer = Main.restaurant.getOrderByCustomer();
		if (ordersByCustomer.get(LoginController.getCustomer()) != null)
			ordersTV.getItems().addAll(ordersByCustomer.get(LoginController.getCustomer()));
	}

	@FXML  // remove order from current customer orders history
	private void remove(ActionEvent event) {
		sounds.clickSound();
		if (ordersTV.getSelectionModel().getSelectedItem() == null)
			return;
		Order o = ordersTV.getSelectionModel().getSelectedItem();
		Main.restaurant.getOrderByCustomer().get(LoginController.getCustomer()).remove(o);
		ordersTV.getItems().remove(ordersTV.getSelectionModel().getSelectedItem());
		Main.changeHaveBeenMade = true;
	}

	@FXML   //go to make order page
	void goMakeOrder(ActionEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/MakeOrder.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		MakeOrderController ctrl = (MakeOrderController) fx.getController();
		ctrl.initData();
		border.setCenter(pp);  // border actually is a pointer to pannelRoot of UserController
	}
}
