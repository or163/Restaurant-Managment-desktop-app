package Edit;

import Audio.sounds;
import Model.Component;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class EditComponentController {

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
	
	@FXML
    private ComboBox<Component> WhichComponent;

	//Fills up the page with current data according to the selected component
	@FXML
    void ComponentSelected(ActionEvent event) {
		Component theSelected = WhichComponent.getSelectionModel().getSelectedItem();
		name.setText(theSelected.getComponentName());
		price.setText(theSelected.getPrice()+"");
		if (theSelected.isHasGluten())
			glutenTG.selectToggle(glutenYes);
		else
			glutenTG.selectToggle(glutenNo);
		if (theSelected.isHasLactose())
			lactoseTG.selectToggle(lactoseYes);
		else
			lactoseTG.selectToggle(lactoseNo);
    }
	
	//Saving the updated data or informs if there is any problem with the info
	@FXML
	void save(ActionEvent event) {
		sounds.clickSound();
		boolean lact = false;
		boolean glut = false;
		if (lactoseYes.isSelected())
			lact = true;
		if (glutenYes.isSelected())
			glut = true;
		double cost = 0;
		if (name.getText() == null || name.getText().isEmpty() || price.getText() == null || price.getText().isEmpty()
				|| lactoseTG.getSelectedToggle() == null || glutenTG.getSelectedToggle() == null
				|| WhichComponent.getSelectionModel().getSelectedItem() == null) {
			message.setText("you have fields that are empty");
			message.setTextFill(Color.RED);
		} else {
			if (!(price.getText().isEmpty()) && Utils.Utils.isDouble(price.getText())) {
				cost = Double.parseDouble(price.getText());
				Component theSelected = WhichComponent.getSelectionModel().getSelectedItem();
				theSelected.setComponentName(name.getText());
				theSelected.setHasGluten(glut);
				theSelected.setHasLactose(lact);
				theSelected.setPrice(cost);
				message.setText("saved succesfully");
				message.setTextFill(Color.GREEN);
				Main.changeHaveBeenMade = true;
			}
			else {
				message.setText("the price is incorrect");
				message.setTextFill(Color.RED);
			}
		}
	}

	
	//fills up the combo box
	public void initData() {
		// TODO Auto-generated method stub
		WhichComponent.getItems().addAll(Main.restaurant.getComponenets().values());
		
	}

}



