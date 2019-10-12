import java.util.LinkedList;//for customer queue

public class CustomerPool
{
	private LinkedList<Customer> customers;
	
	private static CustomerPool ourInstance = new CustomerPool();
	
	public static CustomerPool getInstance()
	{
		return ourInstance;
	}
	
	private CustomerPool()
	{
	
	}
}
