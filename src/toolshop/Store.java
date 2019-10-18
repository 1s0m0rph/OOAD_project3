package toolshop;

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
		ArrayList<Purchasable> options = ((Purchasable) data).getOptions();
		
		//we have the data, now let's make a rental record out of it
		ArrayList<Tool> toolsRented = new ArrayList<>();
		int timeRentedFor = ((Purchasable) data).timeOfRental;//guaranteed because of how customer makes the purchase
		int timeRentedAt = currentTime;
		
		//add all the tools rented into the list
		for(Purchasable opt : options)
		{
			if(opt instanceof Tool)
			{
				toolsRented.add((Tool) opt);
			} else if(opt instanceof ToolDecoratorAdder)//we'll need this for the later ones
			{
				toolsRented.add(((ToolDecoratorAdder) opt).tool);
			}
		}

		dailyRevenue += ((Purchasable) data).getCost();
		RentalRecord rr = new RentalRecord((Customer) subject, toolsRented, timeRentedAt, timeRentedFor);
		rentalRecords.add(rr);
	}
	
	public ArrayList<RentalRecord> getRentalRecords()
	{
		return rentalRecords;
	}
	
	public void returnTools(RentalRecord record)
	{
		for(Tool tool : record.toolsRented)
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
	
	public void incrementDay()
	{
		currentTime++;
		totalRevenue += dailyRevenue;
		dailyRevenue = 0;
		// we can't remove while we iterate, so get indexes to remove
		ArrayList<Integer> removeIdxs = new ArrayList<>(rentalRecords.size());
		for(int i = 0; i < rentalRecords.size(); i++)
		{
			RentalRecord record = rentalRecords.get(i);
			if(record.getDueDate() == currentTime)
			{
				// return tools, in so doing remove the record from the rentalRecords
				record.returnTools();
				removeIdxs.add(i);
			}
		}

		for (int i : removeIdxs)
		{
			rentalRecords.remove(i);
		}
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
