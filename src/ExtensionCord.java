public class ExtensionCord extends PurchaseDecorator
{
	public ExtensionCord(Purchasable purchasable)
	{
		super(purchasable);
	}
	
	public int getCost()
	{
		return purchasable.getCost() + 2;
	}
}
