package UserUI;

import java.io.IOException;

import Audio.sounds;
import Querries.GetCookByExpertiseController;
import Querries.GetPopularComponentsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UserQuerriesController {

    @FXML
    private ComboBox<String> querries;

    @FXML
    private BorderPane border;

    // Initiate combo box with queries names
    public void initData() {
    	querries.getItems().addAll("Get Cook By Expert", "Get Relevant Dish List", "Get Popular Component");
    }
    
    // this method determines which page would display according to user's choice
    @FXML
    void getPage(ActionEvent event) throws IOException {
    	sounds.clickSound();
    	Pane p;
    	AnchorPane pp;
    	FXMLLoader fx;
    	String chosen = querries.getSelectionModel().getSelectedItem();
    	switch(chosen) {
    	case "Get Cook By Expert":  //go to get cook by expertise page
    		fx = new FXMLLoader(getClass().getResource("/View/GetCookByExpertise.fxml"));
    		p = fx.load();
    		pp = (AnchorPane) p;
    		GetCookByExpertiseController ctrl1 = (GetCookByExpertiseController) fx.getController();
    		ctrl1.initData();
    		border.setCenter(pp);
    		break;
    	case "Get Relevant Dish List":  //go to get relevant dish list for specific user
    		fx = new FXMLLoader(getClass().getResource("/View/GRLDishListUser.fxml"));
    		p = fx.load();
    		pp = (AnchorPane) p;
    		GRLDishListUserController ctrl2 = (GRLDishListUserController) fx.getController();
    		ctrl2.initData();
    		border.setCenter(pp);
    		break;
    	case "Get Popular Component":  //go to get popular components page
    		fx = new FXMLLoader(getClass().getResource("/View/GetPopularComponents.fxml"));
    		p = fx.load();
    		pp = (AnchorPane) p;
    		GetPopularComponentsController ctrl3 = (GetPopularComponentsController) fx.getController();
    		ctrl3.initData();
    		border.setCenter(pp);
    		break;
    	}
	}
    
}
