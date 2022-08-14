//Serialization Class

package Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Exceptions.DBManagerException;
import Model.Restaurant;
import javafx.scene.control.Alert;

public class SerializableWiz {
	public static final String SER_PATH="Rest.ser";

	public static void save(Restaurant restaurant) throws DBManagerException {
			restaurant.SaveAllCounters();  //save all counters of id for further using
		
		try {
	         FileOutputStream fileOut = new FileOutputStream(SER_PATH);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(restaurant);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in "+SER_PATH + "\n");
	      } catch (IOException i) {
	    	 throw new DBManagerException("Error in save - "+i.getLocalizedMessage());
	      }
	}

	public static Restaurant read() throws DBManagerException{
		Restaurant restaurant = null;
		try {
	         FileInputStream fileIn = new FileInputStream(SER_PATH);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         restaurant = (Restaurant) in.readObject();
	         in.close();
	         fileIn.close(); 
	         return restaurant;
	      } catch (IOException i) {
	    	  throw new DBManagerException("Error in read - there is no Ser File To read");
	      } catch (ClassNotFoundException c) {
	    	  throw new DBManagerException("Error in read");
	      }
	}


}
