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
		DeterministicCustomer customer = new DeterministicCustomer("The Adversary, Destroyer of Kings, Angel of the Bottomless Pit, Great Beast that is called Dragon, Prince of This World, Father of Lies, Spawn of Satan, and Lord of Darkness");
		customer.rentTools();
		//we're going to basically blackbox all of this now
		//by this point, the observer pattern should have fired and we should see a rental record in the store
		assert(s.getRentalRecords().size() == 1);
		assert(s.getCurrentInventoryCount() == 21);//deterministic customers always rent 3 tools
		RentalRecord rr = s.getRentalRecords().get(0);
		assert(rr.dayRented == 0);
		assert(rr.dayDue == 3);
		assert(rr.toolsRented.size() == 3);
		assert(rr.renter == customer);
		assert(rr.toolsRented.get(0).getCategory().getCategoryName().equalsIgnoreCase("concrete"));
		assert(rr.toolsRented.get(1).getCategory().getCategoryName().equalsIgnoreCase("painting"));
		assert(rr.toolsRented.get(2).getCategory().getCategoryName().equalsIgnoreCase("yardwork"));//all these guaranteed bc of deterministic customer
		rr.returnTools();
		s.getRentalRecords().remove(0);
		assert(s.getDailyRevenue() == 323);//this order should *always* cost 323 for 3 days
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