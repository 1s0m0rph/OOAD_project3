package toolshop;

public class CasualCustomer extends Customer
{
	public CasualCustomer(String name)
	{
		super(name);
	}

	@Override
	public String getType() {
		return "casual";
	}

	@Override
	public int getNumToolsToRent()
	{
		return (int)(Math.random() * 2) + 1;
	}
	
	@Override
	public int getRentalTime()
	{
		return (int)(Math.random() * 2) + 1;
	}
}
