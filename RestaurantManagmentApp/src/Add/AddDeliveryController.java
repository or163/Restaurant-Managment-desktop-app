package Add;

import java.time.LocalDate;
import java.util.TreeSet;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class AddDeliveryController {

	@FXML
	private DatePicker date;

	@FXML
	private RadioButton isDeliverdYes;

	@FXML
	private ToggleGroup deliveyTG;

	@FXML
	private RadioButton isDeliverdNo;

	@FXML
	private Label lblStatus;

	@FXML
	private ListView<Order> orders;

	@FXML
	private ListView<Order> selected;

	@FXML
	private ComboBox<DeliveryPerson> deliveryPersons;

	@FXML
	private ComboBox<DeliveryArea> deliveryArea;

	//Initiate the page and fill the delivery area combo-box with proper options
	public void initData() {
		// TODO Auto-generated method stub
		deliveryArea.getItems().clear();
		orders.getItems().clear();
		deliveryArea.getItems().addAll(Main.restaurant.getAreas().values());
		for (Order o : Main.restaurant.getOrders().values()) {  //Populate orders list view with orders that has no delivery
			if (o.getDelivery() == null)
				orders.getItems().add(o);
		}
		selected.getItems().clear();
	}

	@FXML   // after choosing delivery area ,delivery person combo-box filled with delivery persons from the selected area
	void getDPS(ActionEvent event) {
		sounds.clickSound();
		deliveryPersons.getItems().clear();
		if(deliveryArea.getSelectionModel().getSelectedItem() != null) 
			deliveryPersons.getItems().addAll(deliveryArea.getSelectionModel().getSelectedItem().getDelPersons());
	}
	
	@FXML   // add orders to the selected orders list view
	void addOrder(ActionEvent event) {
		sounds.clickSound();
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

	@FXML  // remove orders from the selected orders list view
	void removeOrder(ActionEvent event) {
    	sounds.clickSound();
    	if(orders.getSelectionModel().getSelectedItem()!=null) {
    		lblStatus.setText("Order removed from the chosen order list");
			lblStatus.setTextFill(Color.BLACK);
    	}
    	else {
    		lblStatus.setText("Please select at list 1 order");
			lblStatus.setTextFill(Color.RED);
    	}
    	selected.getItems().remove(selected.getSelectionModel().getSelectedItem());
    }

	@FXML  //save delivery to the restaurant
	void save(ActionEvent event) {
		sounds.clickSound();
		LocalDate datte = date.getValue();
		boolean isDel = false;
		if (isDeliverdYes.isSelected())
			isDel = true;
		else if (isDeliverdNo.isSelected())
			isDel = false;
		DeliveryPerson delPer = deliveryPersons.getSelectionModel().getSelectedItem();
		DeliveryArea delAre = deliveryArea.getSelectionModel().getSelectedItem();
		try {
			if (delPer == null || delAre == null || selected.getItems().isEmpty() || selected.getItems() == null
					|| datte == null || deliveyTG.getSelectedToggle() == null) {
				lblStatus.setText("Please fill all fields");
				if(datte == null)
					lblStatus.setText("Please choose a date ");
				if(delPer == null)
					lblStatus.setText("Please choose a delivery person ");
				if(delAre == null)
					lblStatus.setText("Please choose a delivery area ");
				lblStatus.setTextFill(Color.RED);
				if(datte == null)
					lblStatus.setText("Please choose a date ");
			} else if (selected.getItems().size() == 1) {
				Order o = selected.getItems().get(0);
				Delivery d = new ExpressDelivery(delPer, delAre, isDel, o, 100, datte);
				if (Main.restaurant.addDelivery(d)) {  //if add succeeds ,clear all fields for further adding
					lblStatus.setText("Delivery was added successfully");
					lblStatus.setTextFill(Color.GREEN);
					Main.changeHaveBeenMade = true;
					deliveryPersons.getSelectionModel().clearSelection();
					deliveryArea.getSelectionModel().clearSelection();
					orders.getSelectionModel().clearSelection();
					selected.getItems().clear();
					deliveyTG.getSelectedToggle().setSelected(false);
					initData();
				} else
					throw new CantAddObjectException("Delivery " + d.getId());
			} else {
				TreeSet<Order> ts = new TreeSet<Order>();
				ts.addAll(selected.getItems());
				Delivery d = new RegularDelivery(ts, delPer, delAre, isDel, datte);
				if (Main.restaurant.addDelivery(d)) {  //if add succeeds ,clear all fields for further adding
					lblStatus.setText("Delivery was added successfully");
					lblStatus.setTextFill(Color.GREEN);
					Main.changeHaveBeenMade = true;
					deliveryPersons.getSelectionModel().clearSelection();
					deliveryArea.getSelectionModel().clearSelection();
					orders.getSelectionModel().clearSelection();
					selected.getItems().clear();
					deliveyTG.getSelectedToggle().setSelected(false);
					initData();
				} else
					throw new CantAddObjectException("Delivery " + d.getId());
			}

		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}
	}
}
