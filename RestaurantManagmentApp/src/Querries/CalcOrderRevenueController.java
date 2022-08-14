package Querries;

import Audio.sounds;
import Model.Order;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CalcOrderRevenueController {

	@FXML
    private ListView<Order> orders;

    @FXML
    private TextField rev;

    // Initiate list view with all orders from restaurant within
	public void initData() {
		// TODO Auto-generated method stub
		orders.getItems().addAll(Main.restaurant.getOrders().values());
	}
	
	@FXML  // calculate selected order revenue
	private void calcOrderRevenue(ActionEvent event) {
		
		if(orders.getSelectionModel().getSelectedItem()!=null) {
			double revenue = orders.getSelectionModel().getSelectedItem().calcOrderRevenue();
			rev.setText(Double.toString(revenue));
			sounds.cashSound();
		}
		else
			sounds.clickSound();
		
	}
}
