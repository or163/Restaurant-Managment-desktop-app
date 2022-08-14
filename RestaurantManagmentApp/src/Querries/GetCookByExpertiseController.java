package Querries;

import java.time.LocalDate;
import java.util.ArrayList;

import Audio.sounds;
import Model.Cook;
import Utils.Expertise;
import Utils.Gender;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GetCookByExpertiseController {

	@FXML
	private TableView<Cook> cooksTV;

	@FXML
	private TableColumn<Cook, Integer> id;

	@FXML
	private TableColumn<Cook, String> fname;

	@FXML
	private TableColumn<Cook, String> lname;

	@FXML
	private TableColumn<Cook, LocalDate> bday;

	@FXML
	private TableColumn<Cook, Gender> gender;

	@FXML
	private TableColumn<Cook, Expertise> expertise;

	@FXML
	private TableColumn<Cook, String> chef;

	@FXML
	private ComboBox<Expertise> expChoice;

	// Initiate combo-box with dishType values, prepare table view as cook table view for further adding
	public void initData() {
		for (Expertise e : Expertise.values())
			expChoice.getItems().add(e);
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		bday.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		expertise.setCellValueFactory(new PropertyValueFactory<>("expert"));
		chef.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().isChef())));
	}

	@FXML // Populate the table view with relevant cook according to expertise selected
	private void getCooksByExpertise(ActionEvent event) {
		sounds.clickSound();
		cooksTV.getItems().clear();
		Expertise e = expChoice.getSelectionModel().getSelectedItem();
		ArrayList<Cook> list = Main.restaurant.GetCooksByExpertise(e);
		for (Cook c : list) {
			cooksTV.getItems().add(c);
		}
	}
}
