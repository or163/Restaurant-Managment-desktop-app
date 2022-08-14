package Add;

import java.io.IOException;

import Audio.sounds;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AddController {
	
	private BorderPane pannelRoot;
	
	public void setPannelRoot(BorderPane pannelRoot) {
		this.pannelRoot = pannelRoot;
	}

	@FXML  // go to add cook page
	void goAddCook(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddCook.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddCookController ctrl = (AddCookController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  // go to add customer page
	void goAddCustomer(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddCustomer.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddCustomerController ctrl = (AddCustomerController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  // go to add delivery person page
	void goAddDP(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddDeliveryPerson.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddDPController ctrl = (AddDPController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  // go to add component page
	void goAddComponent(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddComponent.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to add dish page
	void goAddDish(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddDish.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddDishController ctrl = (AddDishController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to add order page
	void goAddOrder(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddOrder.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddOrderController ctrl = (AddOrderController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to add delivery area page
	void goAddDA(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddDA.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddDAController ctrl = (AddDAController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to add delivery page
	void goAddDelivery(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddDelivery.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddDeliveryController ctrl = (AddDeliveryController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to add customer to black list page
	void goaddCustomerToBlackList (MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/AddCustomerToBlackList.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		AddCustomerToBlackListController ctrl = (AddCustomerToBlackListController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
}
