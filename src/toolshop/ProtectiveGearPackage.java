package toolshop;

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

	public String getType()
	{
		return "Protective Gear Package";
	}
}
