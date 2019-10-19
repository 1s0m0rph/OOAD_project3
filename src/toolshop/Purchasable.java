package toolshop;

import java.util.ArrayList;

public abstract class Purchasable
{
	protected int timeOfRental;
	
	public abstract int getCost();
	
	public abstract Tool getTool();
	
	public abstract ArrayList<PurchaseDecorator> getOptions();
	
	public int getTimeOfRental()
	{
		return timeOfRental;
	}
	
	public void setTimeOfRental(int timeOfRental)
	{
		this.timeOfRental = timeOfRental;
	}
}
