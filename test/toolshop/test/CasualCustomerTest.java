package toolshop.test;

import org.junit.jupiter.api.Test;
import toolshop.*;

class CasualCustomerTest
{
	CasualCustomer cc = new CasualCustomer("Dagon");
	
	@Test
	void getNumToolsToRent()
	{
		//do this a few times to be sure
		for(int i = 0; i < 100; i++)
		{
			int numtools = cc.getNumToolsToRent();
			assert(numtools >= 1);
			assert(numtools <= 2);
		}
	}
	
	@Test
	void getRentalTime()
	{
		//do this a few times to be sure
		for(int i = 0; i < 100; i++)
		{
			int time = cc.getRentalTime();
			assert(time >= 1);
			assert(time <= 2);
		}
	}
}