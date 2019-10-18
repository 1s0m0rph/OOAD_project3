package toolshop.test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import toolshop.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerPoolTest
{
	
	@Test
	@Order(1)
	void getInstance()
	{
		CustomerPool customers = CustomerPool.getInstance();
	}
	
	@Test
	@Order(2)
	void get()
	{
		CustomerPool customers = CustomerPool.getInstance();
		ArrayList<Customer> inStore = new ArrayList<>();
		Customer next = (Customer) customers.get();
		while(next != null)
		{
			inStore.add(next);
			next = (Customer) customers.get();
		}
		assert (inStore.size() == 12);
		for(Customer c : inStore)
		{
			customers.release(c);
		}
	}
	
	@Test
	@Order(3)
	void release()
	{
		CustomerPool customers = CustomerPool.getInstance();
		ArrayList<Customer> inStore = new ArrayList<>();
		Customer next = (Customer) customers.get();
		while(next != null)
		{
			inStore.add(next);
			next = (Customer) customers.get();
		}
		assert(customers.poolCount() == 0);
		for(Customer c : inStore)
		{
			customers.release(c);
		}
		assert(customers.poolCount() == 12);
	}
	
	@Test
	@Order(4)
	void shutdown()
	{
		if(System.getenv("DO_SHUTDOWN_TESTS").equalsIgnoreCase("false"))
			return;//if we do shutdown tests when running everything we're going to run into problems with object pools having been shutdown before tests that depend on them actually run
		CustomerPool customers = CustomerPool.getInstance();
		boolean caughtOnGetNamed = false;
		boolean caughtOnGetNext = false;
		boolean caughtOnRelease = false;
		customers.shutdown();
		
		try
		{
			customers.get("Alice");
		} catch(IllegalArgumentException e)
		{
			caughtOnGetNamed = true;
		}
		try
		{
			customers.get();
		} catch(IllegalArgumentException e)
		{
			caughtOnGetNext = true;
		}
		try
		{
			customers.release(null);
		} catch(IllegalArgumentException e)
		{
			caughtOnRelease = true;
		}
		
		assert (caughtOnGetNamed);
		assert (caughtOnGetNext);
		assert (caughtOnRelease);
	}
}