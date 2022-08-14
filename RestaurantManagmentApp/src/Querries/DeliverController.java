package Querries;

import Audio.sounds;
import Model.Delivery;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class DeliverController {

	@FXML
	private ListView<Delivery> deliveries;

	@FXML
	private Label message;
	
	// Initiate list view with all deliveries that didn't completed yet
	public void initData() {
		// TODO Auto-generated method stub
		for (Delivery d : Main.restaurant.getDeliveries().values()) {
			if (!d.isDelivered())
				deliveries.getItems().add(d);
		}
	}

	@FXML  //  mark this delivery as delivered
	void deliver(ActionEvent event) {
		sounds.clickSound();
		if (!deliveries.getItems().isEmpty()&&deliveries.getSelectionModel().getSelectedItem()!=null) {
			Delivery d = deliveries.getSelectionModel().getSelectedItem();
			Main.restaurant.deliver(d);
			deliveries.getItems().remove(d);
			message.setTextFill(Color.GREEN);
			message.setText("Checked as delivered!");
			Main.changeHaveBeenMade = true;
		}
		else if(deliveries.getItems().isEmpty()){
			message.setTextFill(Color.RED);
			message.setText("There are no deliveris on the list!");
		}
		else {
			message.setTextFill(Color.RED);
			message.setText("Please select a delivery!");
		}
	}

}
