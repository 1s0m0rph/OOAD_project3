package toolshop;

public class AccessoryKit extends PurchaseDecorator
{
	public AccessoryKit(Purchasable purchasable)
	{
		super(purchasable);
	}
	
	public int getCost()
	{
		return purchasable.getCost() + 5;
	}
}
