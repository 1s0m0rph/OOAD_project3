package toolshop;

import toolshop.Customer;

import java.util.ArrayList;

public class RentalRecord
{
	public Customer renter;
	public ArrayList<Tool> toolsRented;
	public int dayRented;
	public int totalRentalTime;
	public int dayDue;
	
	public RentalRecord(Customer renter, ArrayList<Tool> toolsRented, int dayRented, int totalRentalTime)
	{
		this.renter = renter;
		this.toolsRented = toolsRented;
		this.dayRented = dayRented;
		this.totalRentalTime = totalRentalTime;
		
		dayDue = dayRented + totalRentalTime;
	}

	public int getDueDate()
	{
		return dayDue;
	}
}
