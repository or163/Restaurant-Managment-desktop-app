package Edit;

import java.util.List;

import Audio.sounds;
import Model.Component;
import Model.Dish;
import Utils.DishType;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class EditDishController {

    @FXML
    private Label message;

    @FXML
    private TextField name;

    @FXML
    private ChoiceBox<DishType> type;

    @FXML
    private ListView<Component> comps;
    
    @FXML
    private ListView<Component> selected;

    @FXML
    private TextField time;
    
    @FXML
    private ComboBox<Dish> WhichDish;

  //Fills up the page with current data according to the selected dish
    @FXML
    void DishSelected(ActionEvent event) {
    	sounds.clickSound();
		name.clear();
		time.clear();
		type.getSelectionModel().clearSelection();
		comps.getSelectionModel().clearSelection();
		selected.getItems().clear();
    	
    	Dish theSelected = WhichDish.getSelectionModel().getSelectedItem();
    	name.setText(theSelected.getDishName());
    	time.setText(theSelected.getTimeToMake()+"");
    	type.setValue(theSelected.getType());
    	selected.getItems().addAll(theSelected.getComponenets());
    	
    	
    }
    
    @FXML //saves the updated info of the dish to the restaurant
	public void save(ActionEvent e) {
    	sounds.clickSound();
		DishType dt = (DishType) type.getSelectionModel().getSelectedItem();
		List<Component> list = selected.getItems();
		int timeToMake = 0;
		if(!(time.getText().isEmpty())&&Utils.Utils.isOnlyDigits(time.getText()))
			timeToMake = Integer.parseInt(time.getText());
		else {
			message.setText("the time is incorrect");
			message.setTextFill(Color.RED);
		}
		
		if (name.getText() == null || name.getText().isEmpty() || time.getText() == null || time.getText().isEmpty() ||
				type == null || comps.getSelectionModel().getSelectedItems() == null || list == null || list.isEmpty()
				|| WhichDish.getSelectionModel().getSelectedItem() == null) {
			message.setText("you have fields that are empty");
			message.setTextFill(Color.RED);
		} else {
			Dish theSelected = WhichDish.getSelectionModel().getSelectedItem();
			theSelected.setDishName(name.getText());
			theSelected.setTimeToMake(timeToMake);
			theSelected.setType(dt);
			while(!theSelected.getComponenets().isEmpty())
				theSelected.removeComponent(theSelected.getComponenets().get(0));
			for(Component c :list) {
				theSelected.addComponent(c);
			}
			message.setText("saved succesfully");
			message.setTextFill(Color.GREEN);
			Main.changeHaveBeenMade = true;
		}
	}

	public void initData() {
		// TODO Auto-generated method stub
		WhichDish.getItems().addAll(Main.restaurant.getDishes().values());
		for (DishType dt : DishType.values())
			type.getItems().add(dt);
		for (Component c : Main.restaurant.getComponenets().values())
			comps.getItems().add(c);
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
