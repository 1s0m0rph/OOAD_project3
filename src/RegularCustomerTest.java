import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegularCustomerTest
{
	RegularCustomer customer = new RegularCustomer("The Adversary, Destroyer of Kings, Angel of the Bottomless Pit, Great Beast that is called Dragon, Prince of This World, Father of Lies, Spawn of Satan, and Lord of Darkness");
	
	@Test
	void getNumToolsToRent()
	{
		for(int i = 0; i < 100; i++)//do it a few times to be sure
		{
			int number = customer.getNumToolsToRent();
			assert(number <= 3);
			assert(number >= 1);
		}
	}
	
	@Test
	void getRentalTime()
	{
		for(int i = 0; i < 100; i++)//do it a few times to be sure
		{
			int number = customer.getRentalTime();
			assert(number <= 5);
			assert(number >= 3);
		}
	}
	
	@Test
	void rentTools()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		customer.rentTools();
		assert(tsi.getNumToolsCurrentlyInInventory() < 24);//simple test, but by itself customer can't do much; we need to be testing store really
	}
}