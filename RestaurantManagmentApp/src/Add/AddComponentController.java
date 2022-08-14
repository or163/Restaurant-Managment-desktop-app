package Add;

import Audio.sounds;
import Exceptions.CantAddObjectException;
import Model.Component;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class AddComponentController {

	@FXML
	private TextField name;

	@FXML
	private RadioButton lactoseYes;

	@FXML
	private ToggleGroup lactoseTG;

	@FXML
	private RadioButton lactoseNo;

	@FXML
	private RadioButton glutenYes;

	@FXML
	private ToggleGroup glutenTG;

	@FXML
	private RadioButton glutenNo;

	@FXML
	private TextField price;

	@FXML
	private Label message;

	@FXML    //save component to the restaurant
	void save(ActionEvent event) {
		sounds.clickSound();
		boolean lact = false;
		boolean glut = false;
		if (lactoseYes.isSelected())
			lact = true;
		if (glutenYes.isSelected())
			glut = true;
		double cost = 0;
		try {				//validates the are no empty fields
			if (name.getText() == null || name.getText().isEmpty() || price.getText() == null
					|| price.getText().isEmpty() || lactoseTG.getSelectedToggle() == null
					|| glutenTG.getSelectedToggle() == null) {
				message.setText("you have fields that are empty");
				message.setTextFill(Color.RED);
			} else {
				if (!(price.getText().isEmpty()) && Utils.Utils.isDouble(price.getText())) {
					cost = Double.parseDouble(price.getText());
					Component comp = new Component(name.getText(), lact, glut, cost);
					if (Main.restaurant.addComponent(comp)) { //if add succeeds ,clear all fields for further adding
						message.setText("saved succesfully");
						message.setTextFill(Color.GREEN);
						Main.changeHaveBeenMade = true;
						lactoseTG.getSelectedToggle().setSelected(false);
						glutenTG.getSelectedToggle().setSelected(false);
						name.clear();
						price.clear();
					} else
						throw new CantAddObjectException("Component " + comp.getComponentName());
				} else {
					message.setText("the price is incorrect");
					message.setTextFill(Color.RED);
				}
			}
		} catch (CantAddObjectException ex) {
			ex.alertMessage();
		}
	}

}
