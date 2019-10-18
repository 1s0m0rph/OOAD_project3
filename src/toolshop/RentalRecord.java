package toolshop;

import toolshop.Customer;

import java.util.ArrayList;

public class RentalRecord
{
	public Customer renter;
	public ArrayList<Purchasable> toolsRented;
	public int dayRented;
	public int totalRentalTime;
	public int dayDue;
	
	public RentalRecord(Customer renter, ArrayList<Purchasable> toolsRented, int dayRented, int totalRentalTime)
	{
		this.renter = renter;
		this.toolsRented = toolsRented;
		this.dayRented = dayRented;
		this.totalRentalTime = totalRentalTime;

		dayDue = dayRented + totalRentalTime;
	}

	public void returnTools()
	{
		renter.returnTools(toolsRented.size());
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		for (Purchasable t : toolsRented)
		{
			tsi.release(t);
		}
	}

	public int getDueDate()
	{
		return dayDue;
	}
}
