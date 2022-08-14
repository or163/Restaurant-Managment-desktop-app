package Edit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import Audio.sounds;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class EditDeliveryController {

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
	private ChoiceBox<DeliveryPerson> deliveryPersons;

	@FXML
	private ChoiceBox<DeliveryArea> deliveryArea;

	@FXML
	private ComboBox<Delivery> WhichDel;

	private ArrayList<Order> removedOrders = new ArrayList<>(); // holds all removed orders from specific delivery for
																// further adding to orders choice after saving

	// Fills up the page with current data according to the selected Delivery
	@FXML
	void DelSelected(ActionEvent event) {
		deliveryPersons.getSelectionModel().clearSelection();
		deliveryArea.getSelectionModel().clearSelection();
		orders.getSelectionModel().clearSelection();

		orders.getItems().clear();
		selected.getItems().clear();
		Delivery del = WhichDel.getSelectionModel().getSelectedItem();
		if (del != null) {
			date.setValue(del.getDeliveredDate());
			if (del.isDelivered())
				deliveyTG.selectToggle(isDeliverdYes);
			else
				deliveyTG.selectToggle(isDeliverdNo);
			deliveryArea.setValue(del.getArea());
			deliveryPersons.setValue(del.getDeliveryPerson());
			if (del instanceof ExpressDelivery)
				selected.getItems().addAll(((ExpressDelivery) del).getOrder());
			if (del instanceof RegularDelivery)
				selected.getItems().addAll(((RegularDelivery) del).getOrders());
			orders.getItems().addAll(selected.getItems()); // add selected delivery orders
			for (Order o : Main.restaurant.getOrders().values()) { // Populate orders list view with orders that has no
																	// delivery
				if (o.getDelivery() == null)
					orders.getItems().add(o);
			}
		}
		removedOrders.clear(); // if changing delivery without save
	}

	// add orders to the selected orders list view
	@FXML
	void addOrder(ActionEvent event) {
		sounds.clickSound();
		if (selected.getItems().contains(orders.getSelectionModel().getSelectedItem())) {
			lblStatus.setText("Can't contain duplications");
			lblStatus.setTextFill(Color.RED);
		} else if (orders.getSelectionModel().getSelectedItem() == null) {
			lblStatus.setText("Please select at list 1 order");
			lblStatus.setTextFill(Color.RED);
		} else {
			removedOrders.remove(orders.getSelectionModel().getSelectedItem());
			selected.getItems().add(orders.getSelectionModel().getSelectedItem());
			lblStatus.setText("Order added to the chosen orders list");
			lblStatus.setTextFill(Color.BLACK);
		}
	}

	@FXML // remove orders from the selected orders list view
	void removeOrder(ActionEvent event) {
		sounds.clickSound();
		if (selected.getSelectionModel().getSelectedItem() != null) {
			lblStatus.setText("Order removed from the chosen order list");
			lblStatus.setTextFill(Color.BLACK);
			removedOrders.add(selected.getSelectionModel().getSelectedItem());
			selected.getItems().remove(selected.getSelectionModel().getSelectedItem());
		} else {
			lblStatus.setText("Please select at least 1 order");
			lblStatus.setTextFill(Color.RED);
		}
		if (selected.getItems().size() == 0) {
			lblStatus.setText("Note you cant save with 0 orders, please add order to save");
			lblStatus.setTextFill(Color.RED);
		}
	}

	@FXML // save delivery to the restaurant or alerts on missing info, if needed changes
			// the type of delivery
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
		Delivery del = WhichDel.getSelectionModel().getSelectedItem();
		if (delPer == null || delAre == null || selected.getItems().isEmpty() || selected.getItems() == null
				|| datte == null || deliveyTG.getSelectedToggle() == null) {
			lblStatus.setText("Please fill up all fields");
			if (datte == null)
				lblStatus.setText("Please choose a date ");
			if (delPer == null)
				lblStatus.setText("Please choose a delivery person ");
			if (delPer == null)
				lblStatus.setText("Please choose a delivery area ");
			lblStatus.setTextFill(Color.RED);
		} else {
			for (Order o : removedOrders)
				o.setDelivery(null);
			if (selected.getItems().size() == 1 && del instanceof ExpressDelivery) {
				del.setArea(delAre);
				del.setDelivered(isDel);
				del.setDeliveredDate(datte);
				del.setDeliveryPerson(delPer);
				((ExpressDelivery) del).setOrder(selected.getItems().get(0));
				selected.getItems().get(0).setDelivery(del);
			} else if (selected.getItems().size() == 1 && del instanceof RegularDelivery) {
				int id = del.getId();
				Main.restaurant.getDeliveries().values().remove(del);
				Delivery d = new ExpressDelivery(delPer, delAre, isDel, null, datte);
				((ExpressDelivery) d).setOrder(selected.getItems().get(0));
				d.setId(id);
				Main.restaurant.addDelivery(d);
				Delivery.setIdCounter(Delivery.getIdCounter() - 1);
			} else if (selected.getItems().size() > 1 && del instanceof ExpressDelivery) {
				int id = del.getId();
				Main.restaurant.getDeliveries().values().remove(del);
				TreeSet<Order> ts = new TreeSet<Order>();
				ts.addAll(selected.getItems());
				Delivery d = new RegularDelivery(ts, delPer, delAre, isDel, datte);
				d.setId(id);
				Main.restaurant.addDelivery(d);
				Delivery.setIdCounter(Delivery.getIdCounter() - 1);

			} else {// RegularDelivery
				SortedSet<Order> ss = ((RegularDelivery) del).getOrders();
				del.setArea(delAre);
				del.setDelivered(isDel);
				del.setDeliveredDate(datte);
				del.setDeliveryPerson(delPer);
				while (!ss.isEmpty())
					((RegularDelivery) del).removeOrder(ss.first());
				for (Order o : selected.getItems()) {
					((RegularDelivery) del).addOrder(o);
					o.setDelivery(del);
				}
			}
			initData();
			lblStatus.setText("Delivery was added successfully");
			lblStatus.setTextFill(Color.GREEN);
			Main.changeHaveBeenMade = true;
		}
	}

	public void initData() {
		// TODO Auto-generated method stub
		deliveryPersons.getItems().clear();
		deliveryArea.getItems().clear();
		date.setValue(null);
		orders.getItems().clear();
		WhichDel.getItems().clear();
		WhichDel.getSelectionModel().clearSelection();
		deliveryPersons.getItems().addAll(Main.restaurant.getDeliveryPersons().values());
		deliveryArea.getItems().addAll(Main.restaurant.getAreas().values());
		WhichDel.getItems().addAll(Main.restaurant.getDeliveries().values());
		selected.getItems().clear();
	}
}
