package toolshop;

import javax.print.attribute.standard.RequestingUserName;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Store implements Observer
{
	private ArrayList<RentalRecord> rentalRecords;
	private ToolShopInventory inventory = ToolShopInventory.getInstance();
	private static Store ourInstance = new Store();
	private int currentTime = 0;//may want to move this
	private int dailyRevenue = 0;
	private int totalRevenue = 0;
	
	public static Store getInstance()
	{
		return ourInstance;
	}
	
	private Store()
	{
		rentalRecords = new ArrayList<>(24);//we won't ever have more than this
	}
	
	/*
	The customer will give us the Purchasable that they wish to purchase
	
	because of how we built the decorator, this object will tell us all that we need to know:
		total cost
		tools rented
		time of rental
	 */
	public void update(Observable subject, Object data)
	{
		// cast data to an ArrayList of Purchasables
		ArrayList<Purchasable> toolsRented = (ArrayList<Purchasable>) data;
		// get the rental period from the first object
		int timeRentedFor = toolsRented.get(0).getTimeOfRental();//guaranteed because of how customer makes the purchase
		int timeRentedAt = currentTime;

		// we can just pass the list of tools rented directly to the rental record
		for (Purchasable p : toolsRented)
		{
			dailyRevenue += p.getCost();
		}
		RentalRecord rr = new RentalRecord((Customer) subject, toolsRented, timeRentedAt, timeRentedFor);
		rentalRecords.add(rr);
	}
	
	public ArrayList<RentalRecord> getRentalRecords()
	{
		return rentalRecords;
	}
	
	public void returnTools(RentalRecord record)
	{
		for(Purchasable tool : record.toolsRented)
		{
			inventory.release(tool);
		}
	}

	public int getCurrentDay()
	{
		return currentTime;
	}
	
	public int getCurrentInventoryCount()
	{
		return inventory.getNumToolsCurrentlyInInventory();
	}
	
	public ArrayList<RentalRecord> incrementDay()
	{
		currentTime++;
		totalRevenue += dailyRevenue;
		dailyRevenue = 0;
		ArrayList<RentalRecord> completedRentals = new ArrayList<>();
		// iterate backwards so that we can remove records
		for(int i = rentalRecords.size()-1; i >= 0; i--)
		{
			RentalRecord record = rentalRecords.get(i);
			if(record.getDueDate() == currentTime)
			{
				// return tools, in so doing remove the record from the rentalRecords
				record.returnTools();
				completedRentals.add(record);
				rentalRecords.remove(i);
			}
		}
		return completedRentals;
	}

	public ArrayList<RentalRecord> getRentalsForDate(int day)
	{
		ArrayList<RentalRecord> dailyRecords = new ArrayList<>();
		for (RentalRecord rr : rentalRecords)
		{
			if (rr.dayRented == day)
				dailyRecords.add(rr);
		}
		return dailyRecords;
	}

	public int getDailyRevenue()
	{
		return dailyRevenue;
	}
	public int getTotalRevenue()
	{
		return totalRevenue;
	}
}
