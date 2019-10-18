package toolshop;

import toolshop.Purchasable;

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
	
	public ArrayList<Purchasable> getOptions()
	{
		// recursively generate options list
		ArrayList<Purchasable> optionsList = purchasable.getOptions();
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
}
