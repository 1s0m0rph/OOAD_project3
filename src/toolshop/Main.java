package toolshop;

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
			store.incrementDay();
			System.out.printf("Day %d\n", store.getCurrentDay());
			int numCustomers = r.nextInt(customers.poolCount());
			ArrayList<Customer> inStore = new ArrayList<>(numCustomers);
			for (int j=0; j < numCustomers; j++)
			{
				Customer c = (Customer) customers.get();
				c.rentTools();
				customers.release(c);
			}
		}
	}
}
