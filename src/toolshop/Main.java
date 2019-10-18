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
			System.out.printf("\nDay %d\n", store.getCurrentDay());

			// print completed records
			System.out.println("COMPLETED RENTALS");
			for (RentalRecord rr : completedRentals)
			{
				System.out.println(rr);
			}

			// rent new tools
			int numCustomers = r.nextInt(customers.poolCount());
			for (int j=0; j < numCustomers; j++)
			{
				Customer c = (Customer) customers.get();
				c.rentTools();
				customers.release(c);
			}

			// print active rentals
			System.out.println("ACTIVE RENTALS");
			for (RentalRecord rr : store.getRentalRecords())
			{
				System.out.println(rr);
			}

			// print inventory
			System.out.println(ToolShopInventory.getInstance());
			System.out.printf("$%d in revenue today\n", store.getDailyRevenue());
			System.out.println("========");
		}

		System.out.println("\n");
		System.out.println("Total Rentals: " + store.getTotalRentals());
		String[] customerTypes = {"business", "casual", "regular"};
		for (String type : customerTypes)
		{
			System.out.println(type +" rentals: " + store.getRentalsForType(type));
		}
		System.out.println("Total Revenue: $" + store.getTotalRevenue());
	}
}
