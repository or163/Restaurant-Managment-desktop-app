package Querries;

import java.util.LinkedList;

import Model.Component;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GetPopularComponentsController {

	@FXML
	private TableView<Component> compsTV;

	@FXML
	private TableColumn<Component, Integer> id;

	@FXML
	private TableColumn<Component, String> name;

	@FXML
	private TableColumn<Component, String> lactose;

	@FXML
	private TableColumn<Component, String> gluten;

	@FXML
	private TableColumn<Component, Double> price;

	// Initiate table view with popular components in ascending order, the first is the most popular
	public void initData() {
		LinkedList<Component> list = Main.restaurant.getPopularComponents();
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("componentName"));
		lactose.setCellValueFactory(new PropertyValueFactory<>("hasLactose"));
		gluten.setCellValueFactory(new PropertyValueFactory<>("hasGluten"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		for (Component c : list) {  // add components to table view from restaurant.getPopularComponents method
			compsTV.getItems().add(c);
		}
	}
}
