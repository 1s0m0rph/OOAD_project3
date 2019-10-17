package toolshop;

import toolshop.Customer;

import java.util.ArrayList;

public class RentalRecord<T extends Purchasable>
{
	public Customer renter;
	public ArrayList<T> toolsRented;
	public int dayRented;
	public int totalRentalTime;
	public int dayDue;
	
	public RentalRecord(Customer renter, ArrayList<T> toolsRented, int dayRented, int totalRentalTime)
	{
		this.renter = renter;
		this.toolsRented = toolsRented;
		this.dayRented = dayRented;
		this.totalRentalTime = totalRentalTime;
		
		dayDue = dayRented + totalRentalTime;
	}
}
