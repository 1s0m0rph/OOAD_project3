import java.util.ArrayList;

public abstract class PurchaseDecorator extends Purchasable
{
	Purchasable purchasable;
	
	public PurchaseDecorator(Purchasable purchasable)
	{
		super();
		this.purchasable = purchasable;
		
	}
	
	public ArrayList<Purchasable> getOptions()
	{
		ArrayList<Purchasable> retOpts = (ArrayList<Purchasable>) purchasable.getOptions().clone();
		retOpts.add(this);
		return retOpts;
	}
}
