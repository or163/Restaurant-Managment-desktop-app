package application;

import javafx.stage.Stage;

import Model.Restaurant;
import Utils.SerializableWiz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	public static Restaurant restaurant;
	public static Stage stage;
	public static Boolean changeHaveBeenMade = false;  //if any changes made will turn to true, if saved during program stay will turn to false

	@Override //start main stage
	public void start(Stage primaryStage) {
		try {
			Parent p = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			Scene scene = new Scene(p, 700, 500);
			primaryStage.setScene(scene);
			primaryStage.setTitle("JavaEat");
			primaryStage.getIcons().add(new Image("/Icons/restaurant.png"));
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				Utils.Utils.exitButtonAction();
				
			});
			stage = primaryStage;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			restaurant = SerializableWiz.read();
			if (restaurant == null) {  //sign that there is no ser file
				restaurant = Restaurant.getInstance();
			}
			restaurant.SetAllCounters();  //set all id counter from previous launch
			launch(args);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			restaurant = Restaurant.getInstance();
			launch(args);

		}
	}
}