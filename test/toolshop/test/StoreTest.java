package toolshop.test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import toolshop.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoreTest
{
	
	Store s = Store.getInstance();
	
	@Test
	@Order(1)
	void update()
	{
		//we're going to be testing both the customer things and the store itself here, so there's a lot going on
		//use a business one since we get guarantees about the number of tools
		BusinessCustomer customer = new BusinessCustomer("The Adversary, Destroyer of Kings, Angel of the Bottomless Pit, Great Beast that is called Dragon, Prince of This World, Father of Lies, Spawn of Satan, and Lord of Darkness");
		customer.rentTools();
		//we're going to basically blackbox all of this now
		//by this point, the observer pattern should have fired and we should see a rental record in the store
		assert(s.getRentalRecords().size() == 1);
		assert(s.getCurrentInventoryCount() == 21);//business customers always rent 3 tools
		assert(s.getRentalRecords().get(0).dayRented == 0);
		assert(s.getRentalRecords().get(0).dayDue == 7);
		assert(s.getRentalRecords().get(0).toolsRented.size() == 3);
		assert(s.getRentalRecords().get(0).renter == customer);
		//s.returnTools(s.getRentalRecords().get(0));//technically unorthodog but it makes the second test here make more sense
		s.getRentalRecords().get(0).returnTools();
		s.getRentalRecords().remove(0);
	}
	
	@Test
	@Order(2)
	void returnTools()
	{
		BusinessCustomer customer = new BusinessCustomer("Dave");
		customer.rentTools();
		assert(s.getRentalRecords().size() == 1);
		assert(s.getCurrentInventoryCount() == 21);
		RentalRecord record = s.getRentalRecords().get(0);
		int dayDue = record.getDueDate();
		for(int i = s.getCurrentDay(); i < dayDue; i++)
		{
			s.incrementDay();
		}
		assert(s.getRentalRecords().size() == 0);
		assert(s.getCurrentInventoryCount() == 24);
	}
}