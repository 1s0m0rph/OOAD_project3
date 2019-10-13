package toolshop.test;

import org.junit.jupiter.api.Test;
import toolshop.*;

class StoreTest
{
	
	Store s = Store.getInstance();
	
	@Test
	void update()
	{
		//we're going to be testing both the customer things and the store itself here, so there's a lot going on
		RegularCustomer customer = new RegularCustomer("The Adversary, Destroyer of Kings, Angel of the Bottomless Pit, Great Beast that is called Dragon, Prince of This World, Father of Lies, Spawn of Satan, and Lord of Darkness");
		customer.rentTools();
		//we're going to basically blackbox all of this now
		//by this point, the observer pattern should have fired and we should see a rental record in the store
		assert(s.getRentalRecords().size() == 1);
		assert(s.getRentalRecords().get(0).dayRented == 0);
		assert(s.getRentalRecords().get(0).dayDue > 0);
		assert(s.getRentalRecords().get(0).dayDue <= 5);
		assert(s.getRentalRecords().get(0).toolsRented.size() > 0);
		assert(s.getRentalRecords().get(0).toolsRented.size() <= 3);
		assert(s.getRentalRecords().get(0).renter == customer);
	}
}