package toolshop.test;
import toolshop.*;

public class DeterministicCustomer extends Customer
{
	private int optionCount = 0;
	private int toolCatCount = 0;
	public DeterministicCustomer(String name)
	{
		super(name);
	}
	
	@Override
	public int getNumToolsToRent()
	{
		return 3;
	}
	
	@Override
	public int getRentalTime()
	{
		return 3;
	}
	
	/*
	The difference between this and a normal customer is how we pick the other random bits
	 */
	
	/*
	Adding a method allows us to create a test customer that is completely deterministic
	 */
	protected int getToolCategoryIndexToRentFromValid(int maxCategory)
	{
		return toolCatCount++;
	}
	
	/*
	Adding a method allows us to create a test customer that is completely deterministic
	 */
	protected int getNumOptionsToAdd()
	{
		return 3;
	}
	
	/*
	Adding a method allows us to create a test customer that is completely deterministic
	 */
	protected int getOptionIdxToAdd()
	{
		return optionCount++;
	}
}
