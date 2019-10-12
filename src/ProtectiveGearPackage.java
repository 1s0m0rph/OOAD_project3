public class ProtectiveGearPackage extends PurchaseDecorator
{
	public ProtectiveGearPackage(Purchasable purchasable)
	{
		super(purchasable);
	}
	
	public int getCost()
	{
		return purchasable.getCost() + 1;
	}
}
