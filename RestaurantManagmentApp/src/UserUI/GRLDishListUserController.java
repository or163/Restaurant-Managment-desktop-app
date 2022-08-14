package UserUI;

import java.util.Collection;

import Model.Component;
import Model.Dish;
import Utils.DishType;
import application.LoginController;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GRLDishListUserController {

    @FXML
    private TableView<Dish> dishesTV;

    @FXML
    private TableColumn<Dish, Integer> id;

    @FXML
    private TableColumn<Dish, String> name;

    @FXML
    private TableColumn<Dish, DishType> type;

    @FXML
    private TableColumn<Dish, Double> price;

    @FXML
    private TableColumn<Dish, Integer> time;

    @FXML
    private TableColumn<Dish, Component> comps;

    // Initiate table view with relevant dish list for current user on system according to his sensitivities
    // LoginController.getCustomer() refers to current user in system
    public void initData() {
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
    	type.setCellValueFactory(new PropertyValueFactory<>("type"));
    	price.setCellValueFactory(new PropertyValueFactory<>("price"));
    	time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
    	comps.setCellValueFactory(new PropertyValueFactory<>("componenets"));
    	Collection<Dish> list = Main.restaurant.getReleventDishList(LoginController.getCustomer());  
    	for(Dish d : list)
    		dishesTV.getItems().add(d);
    }
}
