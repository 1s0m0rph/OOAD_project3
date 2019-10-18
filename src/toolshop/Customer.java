package toolshop;

import toolshop.AccessoryKit;

import java.util.*;

public abstract class Customer extends Observable
{
	private String name;
	private int toolsRented;

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
		int numToolsToRent = getNumToolsToRent();
		int rentalTime = getRentalTime();
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		
		//do a sanity check to make sure there are actually enough tools in the inventory
		if(tsi.getNumToolsCurrentlyInInventory() < numToolsToRent)
			return;//they don't have what the customer wants, so the customer just leaves
		
		Purchasable p = null;
		
		int toolsRentedSoFar = 0;
		ArrayList<String> possibleToolCats = new ArrayList(Arrays.asList(tsi.getCategoryCountsCurrent().keySet().toArray()));//is this a safe conversion?
		while(toolsRentedSoFar < numToolsToRent)
		{
			//pick a random category
			int catidx = rand.nextInt(possibleToolCats.size());
			Tool tool = (Tool) tsi.get(possibleToolCats.get(catidx));
			if(tool == null)
			{
				//remove this category from the valid ones and try again
				possibleToolCats.remove(catidx);
			}
			else
			{
				//set the rental time variables now
				tool.setTimeOfRental(rentalTime);//this will need to be reset when the tool is returned
				//add this to the purchase
				if(p == null)
				{
					//add it by assignment
					p = tool;
				}
				else
				{
					//add it by decorator construction
					p = new ToolDecoratorAdder(p,tool);
					p.setTimeOfRental(rentalTime);//otherwise we get zeroes propagated
				}
				toolsRentedSoFar++;//we successfully added a tool
				p = tool;
			}
		}
		
		//now we have all the tools in the purchasable object, let's add the options
		int numOptionsToAdd = rand.nextInt(6);
		for(int i = 0; i < numOptionsToAdd; i++)
		{
			//pick a random option to add
			int ri = rand.nextInt(3);
			PurchaseDecorator accessory = null;
			switch(ri)
			{
				case 0:
					accessory = new AccessoryKit(p);
					break;
				case 1:
					accessory = new ExtensionCord(p);
					break;
				case 2:
					accessory = new ProtectiveGearPackage(p);
					break;
				default:
					throw new IllegalArgumentException("randint doesn't work how I think it does. This should never happen.");
			}
			accessory.setTimeOfRental(rentalTime);//set the rental time even for the accessory objects to make our lives easier later on
			p = accessory;
		}

		toolsRented = toolsRentedSoFar;
		//now we have the purchaseable and because of how we handled it, the items have been removed from the inventory
		//notify the store of the purchase
		setChanged();//java weirdness requires this
		notifyObservers(p);//note that because of the java weirdness we don't need to explicitly tell the store who did the purchase; it's part of the observer implementation
	}
}
