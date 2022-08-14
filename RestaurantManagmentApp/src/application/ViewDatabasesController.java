package application;

import Audio.sounds;
import Model.Component;
import Model.Cook;
import Model.Customer;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Dish;
import Model.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ViewDatabasesController {

	@FXML
	private ComboBox<String> ChosenData;

	@FXML
	private ListView<Object> LVdb;

	@FXML
	private TextField id;

	@FXML
	private ImageView custPicture;

	@FXML
	private ListView<Order> orderByCustomerLV;

	@FXML
	private Pane pane;

	@FXML
	private Text text;

	@FXML
	private ImageView next;

	@FXML
	private ImageView prev;

	@FXML
	private Text nextText;

	@FXML
	private Text prevText;
	
	 @FXML
	 private Label message;

	// Initiate combo box with all object options
	public void initData() {
		// TODO Auto-generated method stub
		ChosenData.getItems().addAll("Cooks", "Components", "Customers", "Delivery Persons", "Dishes", "Orders",
				"Deliveries", "Delivery Areas", "Black List", "Order by Customer");
		pane.setVisible(false);
		message.setVisible(false);
	}

	@FXML // fill the list view with relevant objects according to user selection
	void GetData(ActionEvent event) {
		sounds.clickSound();
		pane.setVisible(false);
		custPicture.setImage(null);
		message.setVisible(false);

		String chosen = ChosenData.getSelectionModel().getSelectedItem();
		LVdb.getItems().clear(); // clean former objects from list every selection
		switch (chosen) {
		case "Cooks":
			LVdb.getItems().addAll(Main.restaurant.getCooks().values());
			break;
		case "Components":
			LVdb.getItems().addAll(Main.restaurant.getComponenets().values());
			break;
		case "Customers":
			LVdb.getItems().addAll(Main.restaurant.getCustomers().values());
			break;
		case "Delivery Persons":
			LVdb.getItems().addAll(Main.restaurant.getDeliveryPersons().values());
			break;
		case "Dishes":
			LVdb.getItems().addAll(Main.restaurant.getDishes().values());
			break;
		case "Orders":
			LVdb.getItems().addAll(Main.restaurant.getOrders().values());
			break;
		case "Deliveries":
			LVdb.getItems().addAll(Main.restaurant.getDeliveries().values());
			break;
		case "Delivery Areas":
			LVdb.getItems().addAll(Main.restaurant.getAreas().values());
			break;
		case "Black List":
			LVdb.getItems().addAll(Main.restaurant.getBlackList());
			break;
		case "Order by Customer":
			LVdb.getItems().addAll(Main.restaurant.getCustomers().values());
			message.setVisible(true);
			message.setText("Please select customer");
			break;
		}
	}

	@FXML // filter Objects and get relevant object by Id
	private void getObject(ActionEvent event) {
		sounds.clickSound();
		if (!Utils.Utils.isOnlyDigits(id.getText()))
			return;
		if (ChosenData.getSelectionModel().getSelectedItem() == null)
			return;
		String chosen = ChosenData.getSelectionModel().getSelectedItem();
		switch (chosen) {
		case "Cooks":
			Cook cook = Main.restaurant.getRealCook(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(cook);
			break;
		case "Components":
			Component comp = Main.restaurant.getRealComponent(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(comp);
			break;
		case "Customers": // also show customer picture (if exists)
			Customer cust = Main.restaurant.getRealCustomer(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(cust);
			custPicture.setPreserveRatio(false);
			if (cust != null) {
				if (cust.getProfilePicturePath() != null) { // check if picture exists
					custPicture.setImage(new Image(cust.getProfilePicturePath()));
				} else
					custPicture.setImage(new Image("/Icons/no_image_64px.png"));
			}
			break;
		case "Delivery Persons":
			DeliveryPerson dp = Main.restaurant.getRealDeliveryPerson(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(dp);
			break;
		case "Dishes":
			Dish dish = Main.restaurant.getRealDish(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(dish);
			break;
		case "Orders":
			Order order = Main.restaurant.getRealOrder(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(order);
			break;
		case "Deliveries":
			Delivery del = Main.restaurant.getRealDelivery(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(del);
			break;
		case "Delivery Areas":
			DeliveryArea da = Main.restaurant.getRealDeliveryArea(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(da);
			break;
		case "Order by Customer": // also show customer picture (if exists)
			Customer customer = Main.restaurant.getRealCustomer(Integer.parseInt(id.getText()));
			LVdb.getSelectionModel().select(customer);
			custPicture.setPreserveRatio(false);
			if (customer != null) {
				if (customer.getProfilePicturePath() != null) { // check if picture exists
					custPicture.setImage(new Image(customer.getProfilePicturePath()));
				} else
					custPicture.setImage(new Image("/Icons/no_image_64px.png"));
				pane.setVisible(true);
				text.setText(customer.getFirstName() + " Orders");
				orderByCustomerLV.getItems().addAll(Main.restaurant.getOrderByCustomer().get(customer));
			}
			break;
		}

	}

	@FXML // show customer profile picture (if exists)
	private void showCustomerImage() {
		message.setVisible(false);
		orderByCustomerLV.getItems().clear();
		if (ChosenData.getSelectionModel().getSelectedItem() == "Customers"
				|| ChosenData.getSelectionModel().getSelectedItem() == "Black List"
				|| ChosenData.getSelectionModel().getSelectedItem() == "Order by Customer") { // validates we are on
																								// right property,
			// customers
			if (LVdb.getSelectionModel().getSelectedItem() != null) {
				Customer c = (Customer) LVdb.getSelectionModel().getSelectedItem();
				custPicture.setPreserveRatio(false);
				if (c.getProfilePicturePath() != null) { // check if picture exists
					custPicture.setImage(new Image(c.getProfilePicturePath()));
				} else
					custPicture.setImage(new Image("/Icons/no_image_64px.png"));
				if (ChosenData.getSelectionModel().getSelectedItem() == "Order by Customer") {
					pane.setVisible(true);
					next.setVisible(true); //make icons seen in case they are hidden
					nextText.setVisible(true);
					prev.setVisible(true);
					prevText.setVisible(true);
					text.setText(c.getFirstName() + " Orders");
					orderByCustomerLV.getItems().addAll(Main.restaurant.getOrderByCustomer().get(c));
				}
			}
		} else
			custPicture.setImage(null); // in case we are not in customers property, reset picture

	}

	@FXML
	private void navigateOrderByCustomer(MouseEvent event) {
		sounds.clickSound();
		next.setVisible(true); //make icons seen in case they are hidden
		nextText.setVisible(true);
		prev.setVisible(true);
		prevText.setVisible(true);
		Customer c = (Customer) LVdb.getSelectionModel().getSelectedItem();
		ImageView img = (ImageView) event.getSource();
		int index = -1;
		if(img.getId().equals("next")) {
			index = LVdb.getItems().indexOf(c);
			if(index == LVdb.getItems().size() - 1) { //sign there is no next item on list
				next.setVisible(false);
				nextText.setVisible(false);
				return;
			}  //get next customer in list orders 
			c = (Customer) LVdb.getItems().get(index + 1);
			LVdb.getSelectionModel().select(c);
			orderByCustomerLV.getItems().clear();
			orderByCustomerLV.getItems().addAll(Main.restaurant.getOrderByCustomer().get(LVdb.getItems().get(index+1)));
			text.setText(c.getFirstName() + " Orders");
		}
		else {
			index = LVdb.getItems().indexOf(c);
			if(index == 0) { //sign there is no previous item on list
				prev.setVisible(false);
				prevText.setVisible(false);
				return;
			}  //get previous customer in list orders
			c = (Customer) LVdb.getItems().get(index - 1);
			LVdb.getSelectionModel().select(c);
			orderByCustomerLV.getItems().clear();
			orderByCustomerLV.getItems().addAll(Main.restaurant.getOrderByCustomer().get(LVdb.getItems().get(index - 1)));
			text.setText(c.getFirstName() + " Orders");
			}
		custPicture.setPreserveRatio(false); //profile picture change
		if (c.getProfilePicturePath() != null) { // check if picture exists
			custPicture.setImage(new Image(c.getProfilePicturePath()));
		} else
			custPicture.setImage(new Image("/Icons/no_image_64px.png"));
	}

	@FXML
	private void closePane(ActionEvent event) {
		sounds.clickSound();
		pane.setVisible(false);
		message.setVisible(true);
	}
}
