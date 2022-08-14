package Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import Audio.sounds;
import Exceptions.DBManagerException;
import Model.Component;
import Model.Customer;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Utils {

	// a function that blocks future dates on date picker and sets as today as
	// default
	public static void initDate(DatePicker date) {
		Calendar c = Calendar.getInstance();
		TimeZone tz = c.getTimeZone();
		ZoneId id = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		LocalDate ld = LocalDateTime.ofInstant(c.toInstant(), id).toLocalDate();
		date.setDayCellFactory(d -> new DateCell() {
			public void updateItem(LocalDate locd, boolean empty) {
				super.updateItem(locd, empty);
				setDisable(locd.isAfter(ld));
			};
		});
		date.setValue(ld);
	}

	// a function that checks if the string contains only digits
	public static boolean isOnlyDigits(String str) {
		if (str.length() == 0)
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		}
		return true;
	}

	// a function that checks if the string is a double
	public static boolean isDouble(String str) {
		if (str.length() == 0)
			return false;
		int dotCounter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '.')
				dotCounter++;
			if (dotCounter > 1)
				return false;
			if ((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.')
				return false;
		}
		if (dotCounter > 0 && str.length() == 1)
			return false;
		return true;

	}

	// a function the string(password) is good enough and colors the label
	// accordingly
	public static boolean isValidPassword(String password, Label message) {
		boolean isValid = true;
		message.setTextFill(Color.RED);
		if (password.length() > 15 || password.length() < 6) {
			message.setText("Password must be less than 15 and more than 6 characters in length.");
			isValid = false;
		}
		String upperCaseChars = "(.*[A-Z].*)";
		if (!password.matches(upperCaseChars)) {
			message.setText("Password must have atleast one uppercase character");
			isValid = false;
		}
		String lowerCaseChars = "(.*[a-z].*)";
		if (!password.matches(lowerCaseChars)) {
			message.setText("Password must have atleast one lowercase character");
			isValid = false;
		}
		String numbers = "(.*[0-9].*)";
		if (!password.matches(numbers)) {
			message.setText("Password must have atleast one number");
			isValid = false;
		}
		String specialChars = "(.*[!,@,#,$,%,^,&,*,(,),+,=,_,-,<,>,?].*$)";
		if (!password.matches(specialChars)) {
			message.setText("Password must have atleast one special character");
			isValid = false;
		}
		return isValid;
	}

	// checks if this user exists
	public static boolean userNameExists(String name) {
		for (Customer c : Main.restaurant.getCustomers().values()) {
			if (c.getUserName().equals(name))
				return true;
		}
		return false;
	}

	// shows only components name
	public static String getProperComponents(List<Component> comps) {
		Iterator<Component> iterator = comps.iterator();
		String s = "";
		while (iterator.hasNext())
			s += iterator.next().getComponentName() + ". ";
		return s;
	}

	// exit program, if changes have been made, alert pops and asks user if he would like to save before
	public static void exitButtonAction() {
		try {
			if (!Main.changeHaveBeenMade) //in case there are no changes in program
				closeProgram();
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Exit program");
				alert.setHeaderText("You have unsaved changes, do you want to save before exit");
				alert.setContentText("Choose your option.");

				ButtonType yesButton = new ButtonType("Yes");
				ButtonType noButton = new ButtonType("No");
				ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == yesButton){ //save and exit
					SerializableWiz.save(Main.restaurant);
					closeProgram();
				} else if (result.get() == noButton) //exit without saving
					closeProgram();
				  else // cancel, don't exit program
					alert.close();
			}
		} catch (DBManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// exit program with sounds effect
	private static void closeProgram() {
		sounds.backgroundMusicMute();
		sounds.flashBackSound();
		sounds.byebyeSound();
		try {
			Thread.sleep(2600);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		Stage stage = Main.stage;
		stage.close();
	}

	// show or hide password on mouse enter and exit icon area
	public static void showHidePassword(PasswordField passw, TextField showPassw) {
		if (!showPassw.isVisible()) {
			showPassw.setText(passw.getText());
			showPassw.setVisible(true);
		} else {
			showPassw.setVisible(false);
		}
	}
}
