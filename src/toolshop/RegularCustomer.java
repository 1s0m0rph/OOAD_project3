package toolshop;

public class RegularCustomer extends Customer
{
	public RegularCustomer(String name)
	{
		super(name);
	}
	
	@Override
	public String getType()
	{
		return "regular";
	}
	
	@Override
	public int getNumToolsToRent()
	{
		return (int) (Math.random() * 3) + 1;
	}
	
	@Override
	public int getRentalTime()
	{
		return (int) (Math.random() * 3) + 3;
	}
}
