package Querries;

import java.util.TreeSet;

import Audio.sounds;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Order;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class CreateAIMacineController {

	@FXML
	private ComboBox<DeliveryPerson> deliveryPersons;

	@FXML
	private ComboBox<DeliveryArea> deliveryArea;

	@FXML
	private ListView<Order> orders;

	@FXML
	private ListView<Order> selected;

	@FXML
	private Label lblStatus;

	@FXML
	private ListView<Delivery> outcome;

	// Initiate the page and fill the delivery area combo-box with proper options
	public void initData() {
		orders.getItems().clear();
		selected.getItems().clear();
		deliveryArea.getItems().clear();
		deliveryArea.getItems().addAll(Main.restaurant.getAreas().values());
		for (Order o : Main.restaurant.getOrders().values()) { // Populate orders list view with orders that has no
																// delivery
			if (o.getDelivery() == null)
				orders.getItems().add(o);
		}
		selected.getItems().clear();
	}

	@FXML // after choosing delivery area ,deliverypersons combo-box filled with delivery persons from the selected area
	void getDPS(ActionEvent event) {
		deliveryPersons.getItems().clear();
		if (deliveryArea.getSelectionModel().getSelectedItem() != null)
			deliveryPersons.getItems().addAll(deliveryArea.getSelectionModel().getSelectedItem().getDelPersons());
	}

	@FXML // add order to selected orders list view
	void addOrder(ActionEvent event) {
		if (selected.getItems().contains(orders.getSelectionModel().getSelectedItem())) {
			lblStatus.setText("Can't contain duplications");
			lblStatus.setTextFill(Color.RED);
		} else if (orders.getSelectionModel().getSelectedItem() == null) {
			lblStatus.setText("Please select at list 1 order");
			lblStatus.setTextFill(Color.RED);
		} else {
			selected.getItems().add(orders.getSelectionModel().getSelectedItem());
			lblStatus.setText("Order added to the chosen order list");
			lblStatus.setTextFill(Color.BLACK);
		}
	}

	@FXML // remove order from selected orders list view
	void removeOrder(ActionEvent event) {
		if(selected.getSelectionModel().getSelectedItem() != null) {
			selected.getItems().remove(selected.getSelectionModel().getSelectedItem());
			lblStatus.setText("Order removed from the chosen order list");
			lblStatus.setTextFill(Color.BLACK);
		}
		else
		{
			lblStatus.setText("Please select at list 1 order");
			lblStatus.setTextFill(Color.RED);
		}
	}

	@FXML // creates deliveries from given orders using restaurant.createAIMachine method
	void save(ActionEvent event) {
		sounds.clickSound();
		DeliveryPerson delPer = deliveryPersons.getSelectionModel().getSelectedItem();
		DeliveryArea delAre = deliveryArea.getSelectionModel().getSelectedItem();
		if (delPer == null || delAre == null || selected.getItems().isEmpty() || selected.getItems() == null) {
			lblStatus.setText("Please fill all fields");
			lblStatus.setTextFill(Color.RED);
		} else {
			TreeSet<Order> ts = new TreeSet<Order>();
			TreeSet<Delivery> tsResult = new TreeSet<Delivery>();
			ts.addAll(selected.getItems());
			tsResult = Main.restaurant.createAIMacine(delPer, delAre, ts);
			outcome.getItems().addAll(tsResult);
			for (Delivery d : tsResult)
				Main.restaurant.addDelivery(d);  // add all created deliveries to restaurant's deliveries
			lblStatus.setText("Ai Machine worked successfully");
			lblStatus.setTextFill(Color.GREEN);
			Main.changeHaveBeenMade = true;
			deliveryPersons.getSelectionModel().clearSelection();
			deliveryArea.getSelectionModel().clearSelection();
			orders.getSelectionModel().clearSelection();
			selected.getItems().clear();
			initData();
		}
	}

}