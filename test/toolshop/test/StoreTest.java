package toolshop.test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import toolshop.BusinessCustomer;
import toolshop.RentalRecord;
import toolshop.Store;

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
		assert(rr.getDayRented() == 0);
		assert(rr.getDueDate() == 3);
		assert(rr.getToolsRented().size() == 3);
		assert(rr.getRenter() == customer);
		assert(rr.getToolsRented().get(0).getTool().getCategory().getCategoryName().equalsIgnoreCase("concrete"));
		assert(rr.getToolsRented().get(1).getTool().getCategory().getCategoryName().equalsIgnoreCase("painting"));
		assert(rr.getToolsRented().get(2).getTool().getCategory().getCategoryName().equalsIgnoreCase("yardwork"));//all these guaranteed bc of deterministic customer
		rr.returnTools();
		s.getRentalRecords().remove(0);
		assert(s.getDailyRevenue() == 339);//this order should *always* cost 339 for 3 days
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
