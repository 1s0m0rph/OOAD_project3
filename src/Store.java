import java.util.Observer;

public class Store implements Observer
{
	private static Store ourInstance = new Store();
	
	public static Store getInstance()
	{
		return ourInstance;
	}
	
	private Store()
	{
	}
	
	public void update(Customer subject, RentalRecord data)
	{
	
	}
}
