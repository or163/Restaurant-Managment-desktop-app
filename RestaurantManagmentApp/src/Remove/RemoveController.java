package Remove;

import java.io.IOException;

import Audio.sounds;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class RemoveController {
	
	private BorderPane pannelRoot;
	
	public void setPannelRoot(BorderPane pannelRoot) {
		this.pannelRoot = pannelRoot;
	}

	@FXML  // go to remove cook page
	void goRemoveCook(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveCook.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveCookController ctrl = (RemoveCookController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  // go to remove customer page
	void goRemoveCustomer(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveCustomer.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveCustomerController ctrl = (RemoveCustomerController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}

	@FXML  // go to remove delivery person page
	void goRemoveDP(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveDeliveryPerson.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveDPController ctrl = (RemoveDPController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to remove component page
	void goRemoveComponent(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveComponent.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveComponentController ctrl = (RemoveComponentController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  //  go to remove dish page
	void goRemoveDish(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveDish.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveDishController ctrl = (RemoveDishController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to remove order page
	void goRemoveOrder(MouseEvent event) throws IOException {
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveOrder.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveOrderController ctrl = (RemoveOrderController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to remove delivery area page
	void goRemoveDA(MouseEvent event) throws IOException{
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveDA.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveDAController ctrl = (RemoveDAController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
	
	@FXML  // go to remove delivery page
	void goRemoveDelivery(MouseEvent event) throws IOException{
		sounds.clickSound();
		FXMLLoader fx = new FXMLLoader(getClass().getResource("/View/RemoveDelivery.fxml"));
		Pane p = fx.load();
		AnchorPane pp = (AnchorPane) p;
		RemoveDeliveryController ctrl = (RemoveDeliveryController) fx.getController();
		ctrl.initData();
		pannelRoot.setCenter(pp);
	}
}
