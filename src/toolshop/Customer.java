package toolshop;

import toolshop.AccessoryKit;

import java.lang.reflect.Array;
import java.util.*;

public abstract class Customer extends Observable
{
	private String name;
	protected int toolsRented;

	public Customer(String name)
	{
		this.name = name;
		addObserver(Store.getInstance());//right off the bat we want to add the store as an observer
	}

	public String getName() { return name; }

	public abstract int getNumToolsToRent();
	public abstract int getRentalTime();

	public void returnTools(int numReturned)
	{
		toolsRented -= numReturned;
		if (toolsRented < 0)
		{
			throw new IllegalStateException("Customer has negative tools");
		}
	}
	
	/*
	Adding a method allows us to create a test customer that is completely deterministic
	 */
	protected int getToolCategoryIndexToRentFromValid(int maxCategory)
	{
		Random rand = new Random();
		return rand.nextInt(maxCategory);
	}
	
	/*
	Adding a method allows us to create a test customer that is completely deterministic
	 */
	protected int getNumOptionsToAdd()
	{
		Random rand = new Random();
		return rand.nextInt(6);
	}
	
	/*
	Adding a method allows us to create a test customer that is completely deterministic
	 */
	protected int getOptionIdxToAdd()
	{
		Random rand = new Random();
		return rand.nextInt(3);
	}
	
	/*
	A customer can't rent a number of tools that would put their current count above 3, and if the store has fewer than the customer might prefer, they have to rent fewer
	 */
	protected int getMaxToolsRentable()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		return Math.min(tsi.getNumToolsCurrentlyInInventory(),3 - toolsRented);
	}
	
	/*
	Pick some tools to rent and an amount of time to rent them for, and add in the extras
	
	notify the store of the purchase using observer
	 */
	public void rentTools()
	{
		if (toolsRented > 3)
		{
			System.err.printf("Something went wrong! %s has more than 3 tools rented!\n", name);
			return;
		} else if (toolsRented == 3)
		{
			// can't rent more than 3 tools
			return;
		}

		Random rand = new Random();
		// get customer preferences
		int numToolsToRent = Math.min(getNumToolsToRent(),getMaxToolsRentable());//don't rent more than the max, but if what we want is too much we can always just rent not as much
		if(numToolsToRent == 0)
			return;//we don't even show up to the store
		int rentalTime = getRentalTime();
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		
		//do a sanity check to make sure there are actually enough tools in the inventory
		if(tsi.getNumToolsCurrentlyInInventory() < numToolsToRent)
			return;//they don't have what the customer wants, so the customer just leaves

		// create an ArrayList to store the tools we need
		ArrayList<Purchasable> tools = new ArrayList<>(numToolsToRent);
		// get the available categories
		ArrayList<String> possibleToolCats = new ArrayList(Arrays.asList(tsi.getCategoryCountsCurrent().keySet().toArray()));//is this a safe conversion?

		// rent each tool
		for (int i=0; i < numToolsToRent; i++)
		{
			Purchasable tool = null;
			// get a valid tool category
			while (tool == null)
			{
				int catidx = rand.nextInt(possibleToolCats.size());
				tool = (Tool) tsi.get(possibleToolCats.get(catidx));
				if(tool == null)
				{
					//remove this category from the valid ones and try again
					possibleToolCats.remove(catidx);
				}
			}
			//set the rental time variables now
			tool.setTimeOfRental(rentalTime);//this will need to be reset when the tool is returned

			// add options to each tool
			int numOptionsToAdd = getNumOptionsToAdd();
			for(int j = 0; j < numOptionsToAdd; j++)
			{
				//pick a random option to add
				int ri = getOptionIdxToAdd();
				switch(ri)
				{
					case 0:
						tool = new AccessoryKit(tool);
						break;
					case 1:
						tool = new ExtensionCord(tool);
						break;
					case 2:
						tool = new ProtectiveGearPackage(tool);
						break;
					default:
						throw new IllegalArgumentException("randint doesn't work how I think it does. This should never happen.");
				}
			}
			tools.add(tool);
		}

		toolsRented = tools.size();
		//now we have the purchaseable and because of how we handled it, the items have been removed from the inventory
		//notify the store of the purchase
		setChanged();//java weirdness requires this
		notifyObservers(tools);//note that because of the java weirdness we don't need to explicitly tell the store who did the purchase; it's part of the observer implementation
	}
}
