package toolshop;

import java.util.LinkedList;//for customer queue
import java.util.HashMap;

public class CustomerPool implements ObjectPool
{
	private HashMap<String,Customer> customers = new HashMap<>();
	private static boolean isShutdown;
	
	private static CustomerPool ourInstance = new CustomerPool();
	
	public static CustomerPool getInstance()
	{
		return ourInstance;
	}
	
	private CustomerPool()
	{
	    isShutdown = false;
		// 6 casual customers
	    customers.put("Alice", new CasualCustomer("Alice"));
	    customers.put("Bob", new CasualCustomer("Bob"));
	    customers.put("Chuck", new CasualCustomer("Chuck"));
	    customers.put("Dave", new CasualCustomer("Dave"));
		customers.put("Erin", new CasualCustomer("Erin"));
		customers.put("Fiona", new CasualCustomer("Fiona"));
		// 4 regular customers
		customers.put("Gary", new RegularCustomer("Gary"));
		customers.put("Hannah", new RegularCustomer("Hannah"));
		customers.put("Ian", new RegularCustomer("Ian"));
		customers.put("Janet", new RegularCustomer("Janet"));
		// 2 business customers
		customers.put("Karen", new BusinessCustomer("Karen"));
		customers.put("Lewis", new BusinessCustomer("Lewis"));
	}

	@Override
	public Object get(String type)
	{
		if (isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
		if (customers.containsKey(type) && customers.get(type) != null)
		{
			return customers.replace(type, null);
		}
		else
		{
			return null;
		}
	}

	@Override
	public Object get()
	{
		if (isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
	    for (String k : customers.keySet())
		{
		    if (customers.get(k) != null)
			{
				return customers.replace(k, null);
			}
		}
	    return null;
	}

	@Override
	public void release(Object obj)
	{
		if (isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
		Customer c = (Customer) obj;
		String name = c.getName();
	    if (customers.containsKey(name) && customers.get(name) == null)
	    {
	    	customers.put(name, c);
		} else
		{
	    	// this should never happen
			throw new IllegalArgumentException("Something went wrong: could not add a customer");
		}
	}

	@Override
	public void shutdown()
	{
		if (isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
		for (String k : customers.keySet())
		{
			customers.remove(customers.get(k));
		}
		isShutdown = true;
	}
}
