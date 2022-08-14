package Add;

import java.util.ArrayList;
import java.util.List;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Component;
import Model.Dish;
import Utils.DishType;
import Utils.Utils;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AddDishController {

	@FXML
	private Label message;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<DishType> type;

	@FXML
	private ListView<Component> comps;

	@FXML
	private ListView<Component> selected;

	@FXML
	private TextField time;

	//Initiate the page with current date and fill the combo-boxes with proper options
	public void initData() {
		// TODO Auto-generated method stub
		for (DishType dt : DishType.values())
			type.getItems().add(dt);
		for (Component c : Main.restaurant.getComponenets().values())
			comps.getItems().add(c);
	}

	@FXML    //save dish to the restaurant
	public void save(ActionEvent e) {
		sounds.clickSound();
		DishType dt = (DishType) type.getSelectionModel().getSelectedItem();
		List<Component> list = selected.getItems();
		ArrayList<Component> comp = new ArrayList<>(list);
		int timeToMake = 0;
		try {				//validates the are no empty fields
			if (name.getText() == null || name.getText().isEmpty() || time.getText() == null || time.getText().isEmpty()
					|| type == null || comps.getSelectionModel().getSelectedItems() == null || list == null) {
				message.setText("you have fields that are empty");
				message.setTextFill(Color.RED);
			} else {  
				if (!(time.getText().isEmpty())&Utils.isOnlyDigits(time.getText())) {
					timeToMake = Integer.parseInt(time.getText());
					Dish dish = new Dish(name.getText(), dt, comp, timeToMake);
					if (Main.restaurant.addDish(dish)) {  //if add succeeds ,clear all fields for further adding
						message.setTextFill(Color.GREEN);
						message.setText("saved succesfully");
						Main.changeHaveBeenMade = true;
						name.clear();
						time.clear();
						type.getSelectionModel().clearSelection();
						comps.getSelectionModel().clearSelection();
						selected.getItems().clear();
					} else
						throw new CantAddObjectException("Dish " + dish.getDishName());
				}
				else {
					message.setText("the time is incorrect(int)");
					message.setTextFill(Color.RED);
				}
				
			}
		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}
	}

	@FXML  //add component to selected components for dish list view
	private void addComp(ActionEvent e) {
		sounds.clickSound();
		if (comps.getSelectionModel().getSelectedItem() != null) {
			selected.getItems().add(comps.getSelectionModel().getSelectedItem());
			message.setText("Component added to the chosen components list");
			message.setTextFill(Color.BLACK);
		}
		else {
			message.setText("Please select at list 1 Component");
			message.setTextFill(Color.RED);
		}
	}

	@FXML  //remove component from selected components for dish list view
	private void removeComp(ActionEvent e) {
		sounds.clickSound();
		if(selected.getSelectionModel().getSelectedItem()!=null) {
			message.setText("Component removed from the chosen components list");
			message.setTextFill(Color.BLACK);
		}
		else {
			message.setText("Please select at list 1 component");
    		message.setTextFill(Color.RED);
		}
		selected.getItems().remove(selected.getSelectionModel().getSelectedItem());
	}

}
