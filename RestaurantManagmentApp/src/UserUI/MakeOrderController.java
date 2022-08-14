package UserUI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Component;
import Model.Customer;
import Model.Dish;
import Model.Order;
import Utils.DishType;
import application.LoginController;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MakeOrderController {

	@FXML
	private TableView<Dish> dishesTV;

	@FXML
	private TableColumn<Dish, String> name;

	@FXML
	private TableColumn<Dish, Integer> time;

	@FXML
	private TableColumn<Dish, Double> priceLeft;

	@FXML
	private TableColumn<Dish, String> comps;

	@FXML
	private ComboBox<DishType> type;

	@FXML
	private Label priceLabel;

	@FXML
	private TableView<Dish> selected;

	@FXML
	private TableColumn<Dish, String> dishname;

	@FXML
	private TableColumn<Dish, Double> priceRight;

	@FXML
	private Label messageRight;

	@FXML
	private Label messageLeft;

	@FXML
	private Pane editPane;

	@FXML
	private TableView<Component> allComps;

	@FXML
	private TableView<Component> compsInDish;

	@FXML
	private TableColumn<Component, String> compName1;

	@FXML
	private TableColumn<Component, String> compName2;
	
	@FXML
    private Text noChanges;

	private static int first = 1;

	// Initiate page
	public void initData() {
		editPane.setVisible(false);
		dishesTV.setPlaceholder(new Label(""));
		selected.setPlaceholder(new Label("Add dish"));
		type.getItems().addAll(DishType.values());
		name.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		time.setCellValueFactory(new PropertyValueFactory<>("timeToMake"));
		priceLeft.setCellValueFactory(new PropertyValueFactory<>("price"));
		comps.setCellValueFactory(d -> new SimpleStringProperty(
				String.valueOf(Utils.Utils.getProperComponents(d.getValue().getComponenets()))));
	}

	@FXML // get dishes according to dishType selection in the combo-box
	private void getDishes(ActionEvent event) {
		sounds.clickSound();
		dishesTV.getItems().clear();
		DishType dt = type.getSelectionModel().getSelectedItem();
		Collection<Dish> list = Main.restaurant.getDishes().values();
		for (Dish d : list) {
			if (d.getType().equals(dt))
				dishesTV.getItems().add(d);
		}
	}

	@FXML // add dish to selected dishes
	private void addDish(ActionEvent event) {
		sounds.clickSound();
		if (dishesTV.getSelectionModel().getSelectedItem() == null) {
			messageLeft.setTextFill(Color.RED);
			messageLeft.setText("Please select a dish first");
			return;
		}

		selected.getItems().add(dishesTV.getSelectionModel().getSelectedItem());
		dishname.setCellValueFactory(new PropertyValueFactory<>("dishName"));
		priceRight.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceLabel.setText(getPrice(selected.getItems()));
		messageLeft.setTextFill(Color.GREEN);
		messageLeft.setText("Dish was added to the list");
	}

	@FXML // remove dish from selected dishes
	private void removeDish(ActionEvent event) {
		sounds.clickSound();
		selected.getItems().remove(selected.getSelectionModel().getSelectedItem());
		priceLabel.setText(getPrice(selected.getItems()));
	}

	@FXML // add current selected dishes to shopping cart
	private void addToCart(ActionEvent event) {
		sounds.clickSound();
		if (selected.getItems().size() != 0) {
			ArrayList<Dish> list = new ArrayList<>();
			for (Dish d : selected.getItems())
				list.add(d);
			if (MakeOrderController.first > 1)
				ShoppingCartController.getDishList().addAll(list);
			else
				ShoppingCartController.setDishList(list);
			MakeOrderController.first++;
			messageRight.setTextFill(Color.GREEN);
			messageRight.setText("Items now are in cart");
			priceLabel.setText("");
			selected.getItems().clear();
		} else {
			messageRight.setTextFill(Color.RED);
			messageRight.setText("There is no item selected");
		}
	}

	@FXML // make order
	private void makeOrder(ActionEvent event) {
		sounds.clickSound();
		if (selected.getItems().size() != 0) { // size of items in current order aren't 0
			ArrayList<Dish> list = new ArrayList<>();
			for (Dish d : selected.getItems())
				list.add(d);
			Customer c = LoginController.getCustomer(); // getting current user in system
			Order o = new Order(c, list, null);
			Alert alert = new Alert(AlertType.CONFIRMATION); // confirmation alert regarding the order
			alert.setTitle("Order");
			alert.setHeaderText("Are you sure you want to make this order?");
			alert.setContentText(o.toString());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					if (Main.restaurant.addOrder(o)) {
						messageRight.setTextFill(Color.GREEN);
						messageRight.setText("Ordered successfully");
						Main.changeHaveBeenMade = true;
						sounds.bonapatiteSound();
						priceLabel.setText("");
						selected.getItems().clear();
					} else
						throw new CantAddObjectException("Order " + o.getId());
				} catch (CantAddObjectException ex) {
					ex.alertMessage();
				}
			}
		} else {
			messageRight.setTextFill(Color.RED);
			messageRight.setText("There is no item selected");
		}

	}

	@FXML // show edit dish components pane and initiate pane
	private void goEdit(ActionEvent event) {
		sounds.clickSound();
		if (dishesTV.getSelectionModel().getSelectedItem() == null) {
			messageLeft.setTextFill(Color.RED);
			messageLeft.setText("Please select a dish first");
			return;
		}

		allComps.getItems().clear();
		compsInDish.getItems().clear();
		compName1.setCellValueFactory(new PropertyValueFactory<>("componentName"));
		compName2.setCellValueFactory(new PropertyValueFactory<>("componentName"));
		allComps.getItems().addAll(Main.restaurant.getComponenets().values());
		compsInDish.getItems().addAll(dishesTV.getSelectionModel().getSelectedItem().getComponenets());
		editPane.setVisible(true);
	}

	@FXML // add component to new desired dish
	private void addComp(ActionEvent event) {
		sounds.clickSound();
		if (allComps.getSelectionModel().getSelectedItem() == null)
			return;
		compsInDish.getItems().add(allComps.getSelectionModel().getSelectedItem());
	}

	@FXML // remove component from new desired dish
	private void removeComp(ActionEvent event) {
		sounds.clickSound();
		compsInDish.getItems().remove(compsInDish.getSelectionModel().getSelectedItem());
	}

	@FXML // close edit pane
	private void closeEdit(ActionEvent event) {
		sounds.clickSound();
		editPane.setVisible(false);
	}

	@FXML // save new dish according to user selection
	public void saveButton(ActionEvent e) {
		sounds.clickSound();
		if (compsInDish.getItems().size() > 0) {
			ArrayList<Component> components = new ArrayList<>(compsInDish.getItems());
			Dish base = dishesTV.getSelectionModel().getSelectedItem();
			if (!components.equals(base.getComponenets())) {
				Dish d = new Dish(base.getDishName(), base.getType(), components, base.getTimeToMake());
				d.setId(base.getId());
				Dish.setIdCounter(Dish.getIdCounter() - 1); // return counter back where it was before
				dishesTV.getItems().add(d);
				messageLeft.setTextFill(Color.GREEN);
				messageLeft.setText("Dish was added to the menu");
				editPane.setVisible(false);
				return;
			}
		}
		noChanges.setFill(Color.RED);
		noChanges.setText("no changes have been made");
	}

	// Calculate order price
	public static String getPrice(Collection<Dish> dishes) {
		String s = "";
		double sum = 0;
		for (Dish d : dishes)
			sum += d.getPrice();
		sum = Double.parseDouble(new DecimalFormat("##.##").format(sum)); // makes it look as price
		s += sum + "₪";

		return s;
	}
}
