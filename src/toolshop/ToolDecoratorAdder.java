package toolshop;

import toolshop.Purchasable;
import toolshop.PurchaseDecorator;

/*
This is an odd class that lets us add tools using the decorator pattern
 */
public class ToolDecoratorAdder extends PurchaseDecorator
{
	Tool tool;
	public ToolDecoratorAdder(Purchasable purchasable, Tool tool)
	{
		super(purchasable);
		this.tool = tool;
	}
	
	public int getCost()
	{
		return purchasable.getCost() + tool.getCost();
	}

	public String getType() {
		return null;
	}
}
