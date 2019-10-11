import java.util.Observable;
import java.util.Observer;

public class Store implements Observer
{
	private RentalRecord[] rentalRecords;
	private ToolShopInventory inventory = ToolShopInventory.getInstance();
	private static Store ourInstance = new Store();
	
	public static Store getInstance()
	{
		return ourInstance;
	}
	
	private Store()
	{
		rentalRecords = new RentalRecord[24];//we won't ever have more than this
	}
	
	public void update(Observable subject, Object data)
	{
		subject = (Customer)subject;
		data = (RentalRecord)data;
		
	}
}
