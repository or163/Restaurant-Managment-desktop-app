package Utils;

import java.io.Serializable;
import java.util.Comparator;

import Model.Order;

public class OrderComparator implements Comparator<Order>, Serializable {

	@Override
	public int compare(Order o1, Order o2) {
		// TODO Auto-generated method stub
		int result = o1.getDelivery().getDeliveredDate().compareTo(o2.getDelivery().getDeliveredDate());
		if(result != 0)
			return result;
		return o1.getId() - o2.getId();
	}

}
