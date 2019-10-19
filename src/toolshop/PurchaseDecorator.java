package toolshop;

import java.util.ArrayList;

public abstract class PurchaseDecorator extends Purchasable
{
	Purchasable purchasable;
	
	public PurchaseDecorator(Purchasable purchasable)
	{
		super();
		this.purchasable = purchasable;
	}
	
	public Tool getTool()
	{
		return purchasable.getTool();
	}
	
	public ArrayList<PurchaseDecorator> getOptions()
	{
		// recursively generate options list
		ArrayList<PurchaseDecorator> optionsList = purchasable.getOptions();
		optionsList.add(this);
		return optionsList;
	}
	
	public int getCost()
	{
		return purchasable.getCost();
	}
	
	public int getTimeOfRental()
	{
		return purchasable.getTimeOfRental();
	}
	
	public abstract String getType();
}
