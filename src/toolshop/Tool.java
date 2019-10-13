package toolshop;
import java.util.ArrayList;

public class Tool extends Purchasable
{
	private ToolCategory category;
	protected static final int TOOL_BASE_PRICE = 10;
	
	public Tool(ToolCategory category)
	{
		super();
		this.category = category;
	}
	
	public ToolCategory getCategory()
	{
		return category;
	}
	
	public int getCost()
	{
		return (TOOL_BASE_PRICE + category.getPrice()) * timeOfRental;
	}
	
	/*
	We're going to model the purchase as a bunch of these strung together with decorator. Because of that we'll need to be careful when returning the tools
	 */
	public ArrayList<Purchasable> getOptions()
	{
		ArrayList<Purchasable> retOpts = (ArrayList<Purchasable>) options.clone();//we do this differently because the raw tool option will be at the bottom of the purchase decorator
		retOpts.add(this);
		return retOpts;
	}
}
