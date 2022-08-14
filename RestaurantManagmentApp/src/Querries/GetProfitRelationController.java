package Querries;

import Audio.sounds;
import Model.Dish;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GetProfitRelationController {

    @FXML
    private TableView<Dish> dishesTV;

    @FXML
    private TableColumn<Dish, Double> price;

    @FXML
    private TableColumn<Dish, Integer> time;
    
    @FXML
    private TableColumn<Dish, String> name;

    @FXML  // show profit relation using restaurant.getProfitRelation() method
    void profitRelation(ActionEvent event) {
    	sounds.clickSound();
    	dishesTV.getItems().clear();
    	name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
    	price.setCellValueFactory(new PropertyValueFactory<>("price"));
		time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
		dishesTV.getItems().addAll(Main.restaurant.getProfitRelation());
		sounds.cashSound();
    }
}
