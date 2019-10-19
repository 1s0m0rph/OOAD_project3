package toolshop.test;

import org.junit.jupiter.api.Test;
import toolshop.*;

import java.util.ArrayList;

class IntegrationTest {

	@Test
	void customerReturns()
	{
		Store s = Store.getInstance();
		Customer alice = new RegularCustomer("Alice");
		Customer bob = new BusinessCustomer("Bob");
		Customer chuck = new CasualCustomer("Chuck");
		alice.rentTools();
		bob.rentTools();
		chuck.rentTools();
		assert(s.getRentalRecords().size() == 3);
		assert(s.getCurrentInventoryCount() < 24);
		for (int i=0; i<8; i++) 
		{
			s.incrementDay();
		}
		assert(s.getCurrentInventoryCount() == 24);
		assert(s.getRentalRecords().size() == 0);
	}
	
	@Test
	void simulateMonth()
	{
		Store s = Store.getInstance();
		CustomerPool customers = CustomerPool.getInstance();
		ArrayList<Customer> dailyCustomers = new ArrayList<>(12);
		for (int i=0; i<35; i++)
		{
			customers.shuffle();
			Customer next = (Customer) customers.get();
			while (next != null)
			{
				dailyCustomers.add(next);
				next.rentTools();
				next = (Customer) customers.get();
			}
			for (Customer c : dailyCustomers)
			{
				customers.release(c);
			}
			dailyCustomers.clear();
		}
	}
}
