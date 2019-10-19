package toolshop;

public class BusinessCustomer extends Customer
{
	public BusinessCustomer(String name)
	{
		super(name);
	}
	
	@Override
	public String getType()
	{
		return "business";
	}
	
	@Override
	public int getNumToolsToRent()
	{
		return 3;
	}
	
	@Override
	public int getRentalTime()
	{
		return 7;
	}
}
