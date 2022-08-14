package Add;

import java.util.ArrayList;
import java.util.List;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Customer;
import Model.Dish;
import Model.Order;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class AddOrderController {

	@FXML
	private Label message;

	@FXML
	private ListView<Dish> selected;

	@FXML
	private ListView<Customer> custV;

	@FXML
	private ListView<Dish> dishV;

	//Initiate the page with current date and fill the combo-boxes with proper options
	public void initData() {
		// TODO Auto-generated method stub
		for (Customer c : Main.restaurant.getCustomers().values())
			custV.getItems().add(c);
		for (Dish d : Main.restaurant.getDishes().values())
			dishV.getItems().add(d);
	}

	@FXML   //save order to the restaurant
	void save(ActionEvent event) {
		sounds.clickSound();
		Customer cust = custV.getSelectionModel().getSelectedItem();
		List<Dish> list = selected.getItems();
		ArrayList<Dish> dishes = new ArrayList<>(list);
		try {		//validates fields are not null
			if (cust == null || list == null || list.isEmpty()) {
				message.setTextFill(Color.RED);
				message.setText("you have fields that are empty");
			} else {
				Order order = new Order(cust, dishes, null);
				if (Main.restaurant.addOrder(order)) { //if add succeeds ,clear all fields for further adding
					message.setText("saved succesfully");
					message.setTextFill(Color.GREEN);
					Main.changeHaveBeenMade = true;
					custV.getSelectionModel().clearSelection();
					dishV.getSelectionModel().clearSelection();
					selected.getItems().clear();
				} else
					throw new CantAddObjectException("Order " + order.getId());
			}
		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}
	}

	@FXML  //add dish to selected dishes for order list view
	private void addDish(ActionEvent e) {
		sounds.clickSound();
		if (dishV.getSelectionModel().getSelectedItem() != null) {
			selected.getItems().add(dishV.getSelectionModel().getSelectedItem());
			message.setText("Dish added to the chosen dishes list");
			message.setTextFill(Color.BLACK);
		}
		else {
			message.setText("Please select at list 1 dish");
			message.setTextFill(Color.RED);
		}
	}
	

	@FXML  //remove dish from selected dishes for order list view
	private void removeDish(ActionEvent e) {
		sounds.clickSound();
		if(selected.getSelectionModel().getSelectedItem()!=null) {
			message.setText("Dish removed from the chosen dishes list");
			message.setTextFill(Color.BLACK);
			selected.getItems().remove(selected.getSelectionModel().getSelectedItem());
		}
		else {
			message.setText("Please select at list 1 dish");
			message.setTextFill(Color.RED);
		}
		
	}
	
}
