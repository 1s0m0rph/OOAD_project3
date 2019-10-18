package toolshop;

import java.util.ArrayList;

public abstract class Purchasable
{
	ArrayList<Purchasable> options;//we'll add the options to this list when they get added on
	protected int timeOfRental;
	
	public Purchasable()
	{
		options = new ArrayList<Purchasable>();
	}
	
	public abstract int getCost();
	
	abstract public ArrayList<Purchasable> getOptions();

	public int getTimeOfRental()
	{
		return timeOfRental;
	}
	
	public void setTimeOfRental(int timeOfRental)
	{
		this.timeOfRental = timeOfRental;
	}
}
