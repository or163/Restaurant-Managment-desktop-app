package Querries;

import Model.DeliveryArea;
import Model.Order;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class OrderWaitingTimeController {

	@FXML
	private BarChart<String, Integer> timeChart;

	@FXML
	private ComboBox<Order> orders;

	@FXML
	private ComboBox<DeliveryArea> da;

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	@FXML
	private TextField time;

	// Initiate combo-boxes with orders and delivery areas values
	public void initData() {
		orders.getItems().addAll(Main.restaurant.getOrders().values());
		da.getItems().addAll(Main.restaurant.getAreas().values());
	}

	@FXML  // add graph to view according to selected order and delivery area indicates order waiting time
	private void addGraph() {
		if (orders.getSelectionModel().getSelectedItem() != null && da.getSelectionModel().getSelectedItem() != null) {
			Order o = orders.getSelectionModel().getSelectedItem();
			DeliveryArea area = da.getSelectionModel().getSelectedItem();
			int waitingTime = o.orderWaitingTime(area);
			Series<String, Integer> series = new XYChart.Series<>();
			series.getData().add(new XYChart.Data<>("", waitingTime));
			series.setName("Order: " + Integer.toString(o.getId()) + "  Area: " + area.getAreaName());
			timeChart.getData().add(series);
			time.setText(Integer.toString(waitingTime));
		}
	}

	@FXML  // clear the graph when relevant button pressed
	private void clearGraph() {
		timeChart.getData().clear();
		time.setText("");
	}

}
