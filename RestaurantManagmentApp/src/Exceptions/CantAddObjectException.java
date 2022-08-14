package Exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CantAddObjectException extends Exception {

	// when any add method return false, Initiate cant add object exception
	public CantAddObjectException(String name) {
		super("Failed to add " + name + " to restaurant");
		// TODO Auto-generated constructor stub
	}
	
	public void alertMessage() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Failed to add");
		alert.setContentText(this.getMessage());
		alert.showAndWait();
	}

}
