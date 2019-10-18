package toolshop;

import javax.print.attribute.standard.RequestingUserName;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
	public static void main(String[] args)
	{
		Store store = Store.getInstance();
		CustomerPool customers = CustomerPool.getInstance();
		Random r = new Random();

		for (int i=0; i < 35; i++)
		{
			ArrayList<RentalRecord> completedRentals = store.incrementDay();
			System.out.printf("Day %d\n", store.getCurrentDay());
			for (RentalRecord rr : completedRentals)
			{

			}
			int numCustomers = r.nextInt(customers.poolCount());
			for (int j=0; j < numCustomers; j++)
			{
				Customer c = (Customer) customers.get();
				c.rentTools();
				customers.release(c);
			}
			System.out.printf("$%d in revenue today\n", store.getDailyRevenue());
		}
	}
}
