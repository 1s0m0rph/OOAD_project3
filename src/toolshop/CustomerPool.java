package toolshop;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class CustomerPool implements ObjectPool
{
	private ArrayDeque<Customer> customers = new ArrayDeque<>(12);
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
		customers.add(new CasualCustomer("Alice"));
		customers.add(new CasualCustomer("Bob"));
		customers.add(new CasualCustomer("Chuck"));
		customers.add(new CasualCustomer("Dave"));
		customers.add(new CasualCustomer("Erin"));
		customers.add(new CasualCustomer("Fiona"));
		// 4 regular customers
		customers.add(new RegularCustomer("Gary"));
		customers.add(new RegularCustomer("Hannah"));
		customers.add(new RegularCustomer("Ian"));
		customers.add(new RegularCustomer("Janet"));
		// 2 business customers
		customers.add(new BusinessCustomer("Karen"));
		customers.add(new BusinessCustomer("Lewis"));
	}
	
	public void shuffle()
	{
		Random r = new Random();
		Customer[] customerArray = new Customer[12];
		customerArray = customers.toArray(customerArray);
		for(int i = 0; i < customers.size(); i++)
		{
			int randIdx = r.nextInt(customerArray.length);
			Customer tmp = customerArray[i];
			customerArray[i] = customerArray[randIdx];
			customerArray[randIdx] = tmp;
		}
		customers = new ArrayDeque<>(Arrays.asList(customerArray));
	}
	
	public int poolCount()
	{
		return customers.size();
	}
	
	@Override
	public Object get()
	{
		if(isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
		try
		{
			return customers.pop();
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	@Override
	public void release(Object obj)
	{
		if(isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
		customers.push((Customer) obj);
	}
	
	@Override
	public void shutdown()
	{
		if(isShutdown)
			throw new IllegalArgumentException("CustomerPool has been shut down.");
		while(!customers.isEmpty())
		{
			customers.pop();
		}
		isShutdown = true;
	}
}
