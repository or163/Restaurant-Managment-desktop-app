package Edit;

import java.io.IOException;

import Audio.sounds;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class EditController {

	private BorderPane pannelRoot;  // this pannel root is a pointer to ManagerController.pannelRoot our main pannel in manager ui
	
	public void setPannelRoot(BorderPane pannelRoot) {
		this.pannelRoot = pannelRoot;
	}
	@FXML  //Initiate and go to edit cook page
	void goEditCook(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditCook.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditCookController ctrl = (EditCookController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  //Initiate and go to edit customer page
	void goEditCustomer(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditCustomer.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditCustomerController ctrl = (EditCustomerController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  //Initiate and go to edit delivery person page
	void goEditDP(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditDeliveryPerson.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditDPController ctrl = (EditDPController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  //Initiate and go to edit component page
	void goEditComponent(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditComponent.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditComponentController ctrl = (EditComponentController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //Initiate and go to edit dish page
	void goEditDish(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditDish.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditDishController ctrl = (EditDishController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //Initiate and go to edit order page
	void goEditOrder(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditOrder.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditOrderController ctrl = (EditOrderController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //Initiate and go to edit delivery area page
	void goEditDA(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditDA.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditDAController ctrl = (EditDAController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //Initiate and go to edit delivery page
	void goEditDelivery(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/EditDelivery.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		EditDeliveryController ctrl = (EditDeliveryController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
}