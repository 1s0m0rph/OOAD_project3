package toolshop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/*
Object pool for the inventory of the tool shop

 */
public class ToolShopInventory implements ObjectPool
{
	private HashMap<String, LinkedList<Tool>> inventory;//we want to seperate the tools by type and index by a type string
	private int numToolsCurrentlyInInventory;
	private static ToolShopInventory instance = new ToolShopInventory(getInitialToolCounts());
	private static boolean isShutdown = false;
	private HashMap<String, Integer> categoryCountsCurrent;
	
	private static HashMap<String, Integer> getInitialToolCounts()
	{
		HashMap<String, Integer> init = new HashMap<>();
		init.put("concrete", 1);
		init.put("painting", 8);
		init.put("plumbing", 5);
		init.put("woodwork", 6);
		init.put("yardwork", 4);
		return init;
	}
	
	/*
	Start by knowing how many of each type of tool there is
	 */
	private ToolShopInventory(HashMap<String, Integer> toolCounts)
	{
		inventory = new HashMap<>();
		
		SimpleToolFactory stf = new SimpleToolFactory();
		//use the simple tool factory to initialize all of these objects
		Set countKeys = toolCounts.keySet();
		for(Object countKey : countKeys)
		{
			String type = (String) countKey;
			LinkedList<Tool> nextQ = new LinkedList<>();
			//now create all of the tools of this type
			int numTools = toolCounts.get(type);
			for(int i = 0; i < numTools; i++)
			{
				//create a new tool of this type
				Tool tool = stf.createTool(type);
				nextQ.add(tool);//since we added to the end, we'll need to take items from the beginning
			}
			
			
			//finally, add that queue to the hashmap for the tool inventory
			inventory.put(type, nextQ);
		}
		numToolsCurrentlyInInventory = 24;//we'll change this around whenever a tool gets checked out or in
		categoryCountsCurrent = toolCounts;//we start off with everything as it started
	}
	
	public static ToolShopInventory getInstance()
	{
		if(isShutdown)
			throw new IllegalArgumentException("ToolShopInventory has been shut down!");
		return instance;
	}
	
	@Override
	public Object get(String type)
	{
		if(isShutdown)
			throw new IllegalArgumentException("ToolShopInventory has been shut down!");
		Tool ret = null;//it's possible there's no tool left in this category
		if(inventory.get(type).size() > 0)
		{
			ret = inventory.get(type).removeFirst();//get the first tool in the queue of the correct type
			numToolsCurrentlyInInventory--;
			categoryCountsCurrent.put(type, categoryCountsCurrent.get(type) - 1);//update this category's count
		}
		return ret;
	}
	
	@Override
	public void release(Object obj)
	{
		if(isShutdown)
			throw new IllegalArgumentException("ToolShopInventory has been shut down!");
		Tool tool = ((Purchasable) obj).getTool();
		String type = tool.getCategory().getCategoryName();//now we have the type of tool, add it to the correct queue in the hashmap
		tool.setTimeOfRental(0);
		inventory.get(type).add(tool);
		numToolsCurrentlyInInventory++;
		categoryCountsCurrent.put(type, categoryCountsCurrent.get(type) + 1);//update this category's count
	}
	
	@Override
	public void shutdown()
	{
		if(isShutdown)
			throw new IllegalArgumentException("ToolShopInventory has been shut down!");
		Set keys = inventory.keySet();
		for(Object key : keys)
		{
			LinkedList current = inventory.get(key);
			current.clear();
		}
		inventory.clear();
		isShutdown = true;
	}
	
	public int getNumToolsCurrentlyInInventory()
	{
		if(isShutdown)
			throw new IllegalArgumentException("ToolShopInventory has been shut down!");
		return numToolsCurrentlyInInventory;
	}
	
	public HashMap<String, Integer> getCategoryCountsCurrent()
	{
		return categoryCountsCurrent;
	}
	
	public String toString()
	{
		StringBuilder retString = new StringBuilder("INVENTORY\n");
		for(String cat : inventory.keySet())
		{
			retString.append(cat).append(": ").append(inventory.get(cat).size()).append("\n");
		}
		return retString.toString();
	}
}
