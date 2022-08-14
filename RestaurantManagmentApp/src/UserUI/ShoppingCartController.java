package UserUI;

import java.util.ArrayList;
import java.util.Optional;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Customer;
import Model.Dish;
import Model.Order;
import application.LoginController;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class ShoppingCartController {

	@FXML
	private TableView<Dish> dishesTV;

	@FXML
	private TableColumn<Dish, String> name;

	@FXML
	private TableColumn<Dish, Integer> time;

	@FXML
	private TableColumn<Dish, Double> price;

	@FXML
	private TableColumn<Dish, String> comps;

	@FXML
	private Label message;

	@FXML
	private TextField priceField;

	private static ArrayList<Dish> dishList; // indicates if there has been changes in menu & order page and another
												// dishes were added to cart

	public static ArrayList<Dish> getDishList() {
		return dishList;
	}

	public static void setDishList(ArrayList<Dish> dishes) {
		dishList = dishes;
	}

	// Initiate table view with dishes that have been sent to shopping cart through
	// menu & order page
	public void initData() {
		// TODO Auto-generated method stub
		dishesTV.setPlaceholder(new Label("There are no items in cart"));
		name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		comps.setCellValueFactory(d -> new SimpleStringProperty(
				String.valueOf(Utils.Utils.getProperComponents(d.getValue().getComponenets()))));
		if (dishList != null) {
			dishesTV.getItems().addAll(dishList);
			priceField.setText(MakeOrderController.getPrice(dishList)); // sets price to the order
		}
	}

	@FXML // make the order, alert would pop if customer sure about this, ok select will make the order
	private void makeOrder(ActionEvent event) {
		sounds.clickSound();
		if (dishesTV.getItems().size() != 0) {
			Customer c = LoginController.getCustomer();  //getting current user in system
			ArrayList<Dish> list = new ArrayList<>();
			list.addAll(dishesTV.getItems());
			Order o = new Order(c, list, null);
			Alert alert = new Alert(AlertType.CONFIRMATION);  //confirmation alert regarding the order
			alert.setTitle("Order");
			alert.setHeaderText("Are you sure you want to make this order?");
			alert.setContentText(o.toString());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					if (Main.restaurant.addOrder(o)) {
						message.setTextFill(Color.GREEN);
						message.setText("Ordered successfully");
						Main.changeHaveBeenMade = true;
						sounds.bonapatiteSound();
						priceField.setText("");
						dishesTV.getItems().clear(); // after order succeeds resetting the table view of items in cart
						dishList.clear();
					} else
						throw new CantAddObjectException("Order " + o.getId());
				} catch (CantAddObjectException ex) {
					ex.alertMessage();
				}
			} else
				;
		} else {
			message.setTextFill(Color.RED);
			message.setText("There are no items in cart!");
		}
	}

	@FXML // remove dish from current shopping cart
	private void removeDish(ActionEvent event) {
		sounds.clickSound();
		dishList.remove(dishesTV.getSelectionModel().getSelectedItem());
		dishesTV.getItems().remove(dishesTV.getSelectionModel().getSelectedItem());
		priceField.setText(MakeOrderController.getPrice(dishesTV.getItems()));
	}

}
