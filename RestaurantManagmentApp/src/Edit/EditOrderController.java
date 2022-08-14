package Edit;

import java.util.ArrayList;
import java.util.List;
import Audio.sounds;
import Model.Customer;
import Model.Dish;
import Model.Order;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class EditOrderController {

	@FXML
	private Label message;

	@FXML
	private ListView<Dish> selected;

	@FXML
	private ListView<Dish> dishV;
	
	@FXML
    private ComboBox<Order> WhichOrder;
	
	@FXML
    private ComboBox<Customer> WhichCust;

	//Fills up the page with current data according to the selected order
    @FXML
    void OrderSelected(ActionEvent event) {
    	sounds.clickSound();
    	Order theSelected = WhichOrder.getSelectionModel().getSelectedItem();
    	WhichCust.setValue(theSelected.getCustomer());
    	selected.getItems().clear();
    	selected.getItems().addAll(theSelected.getDishes());
    }

	@FXML //save order to the restaurant
    void save(ActionEvent event) {
		sounds.clickSound();
    	List<Dish> list = selected.getItems();
		ArrayList<Dish> dishes = new ArrayList<>(list);
		if(WhichOrder.getSelectionModel().getSelectedItem() == null) {
			message.setText("Please select order");
			message.setTextFill(Color.RED);
		}
		else if (list == null ||list.isEmpty()) {
			message.setText("Please add at list 1 dish");
			message.setTextFill(Color.RED);
		} else {
			Order theSelected = WhichOrder.getSelectionModel().getSelectedItem();
			theSelected.setCustomer(WhichCust.getValue());
			while(!theSelected.getDishes().isEmpty())
				theSelected.removeDish(theSelected.getDishes().get(0));
			for(Dish d: dishes)
				theSelected.addDish(d);
			message.setText("saved succesfully");
			message.setTextFill(Color.GREEN);
			Main.changeHaveBeenMade = true;
			dishV.getSelectionModel().clearSelection();
		}
    }
		
	//Initiate the page with current date and fill the combo-boxes with proper options
	public void initData() {
		// TODO Auto-generated method stub
		WhichOrder.getItems().addAll(Main.restaurant.getOrders().values());
		WhichCust.getItems().addAll(Main.restaurant.getCustomers().values());
		for (Dish d : Main.restaurant.getDishes().values())
			dishV.getItems().add(d);
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
