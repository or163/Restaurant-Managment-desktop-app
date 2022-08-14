package Edit;

import java.time.LocalDate;

import Audio.sounds;
import Model.Cook;
import Utils.Expertise;
import Utils.Gender;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class EditCookController {

    @FXML
    private TextField txtFName;

    @FXML
    private TextField txtLName;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<Gender> gender;

    @FXML
    private ChoiceBox<Expertise> expertise;

    @FXML
    private RadioButton isChefYes;

    @FXML
    private ToggleGroup chefTG;

    @FXML
    private RadioButton isChefNo;

    @FXML
    private Label message;
    
    @FXML
    private ComboBox<Cook> WhichCook;

    
  
  	//Saving the updated data or informs if there is any problem with the info
    @FXML
    void save(ActionEvent event) {
    	sounds.clickSound();
    	Gender gend = gender.getSelectionModel().getSelectedItem();
		Expertise expert = expertise.getSelectionModel().getSelectedItem();
		boolean chef = false;
		if (isChefYes.isSelected())
			chef = true;
		else if (isChefNo.isSelected())
			chef = false;
		LocalDate bday = date.getValue();
		String Fname = txtFName.getText();
		String Lname = txtLName.getText();
		if (txtFName.getText() == null || txtFName.getText().isEmpty() || txtLName.getText() == null || txtLName.getText().isEmpty() ||
				gend == null || expert == null || chefTG.getSelectedToggle() == null) {
			message.setText("you have fields that are empty");
			message.setTextFill(Color.RED);
		}
		else if(bday == null){
			message.setText("Date must be mm/dd/yyyy");
			message.setTextFill(Color.RED);
		}
		else {
			Cook theSelectedCook = WhichCook.getSelectionModel().getSelectedItem();
			theSelectedCook.setBirthDay(bday);
			theSelectedCook.setChef(chef);
			theSelectedCook.setExpert(expert);
			theSelectedCook.setFirstName(Fname);
			theSelectedCook.setLastName(Lname);
			theSelectedCook.setGender(gend);
			message.setText("saved succesfully");
			message.setTextFill(Color.GREEN);
			Main.changeHaveBeenMade = true;
		}
	}
    
    //Fills up the page with current data according to the selected cook
    @FXML
    void CookSelected(ActionEvent event) {
    	gender.getItems().clear();
    	expertise.getItems().clear();
		for (Gender g : Gender.values())
			gender.getItems().add(g);
		for (Expertise n : Expertise.values())
			expertise.getItems().add(n);
	    Cook theSelectedCook = WhichCook.getSelectionModel().getSelectedItem();
	    txtFName.setText(theSelectedCook.getFirstName());
	    txtLName.setText(theSelectedCook.getLastName());
	    date.setValue(theSelectedCook.getBirthDay());
	    gender.setValue(theSelectedCook.getGender());
	    expertise.setValue(theSelectedCook.getExpert());
	    if (theSelectedCook.isChef())
	    	chefTG.selectToggle(isChefYes);
		else
			chefTG.selectToggle(isChefNo);
    }
    
	public void initData() {

		WhichCook.getItems().addAll(Main.restaurant.getCooks().values());
		// TODO Auto-generated method stub
		
	}

}
