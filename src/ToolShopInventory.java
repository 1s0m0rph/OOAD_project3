import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Set;

/*
Object pool for the inventory of the tool shop

TODO: add tests for all of this functionality
 */
public class ToolShopInventory implements ObjectPool
{
	private HashMap<String,LinkedList<Tool>> inventory;//we want to seperate the tools by type and index by a type string
	private int numToolsCurrentlyInInventory;
	private static ToolShopInventory instance = new ToolShopInventory(getInitialToolCounts());
	private boolean isShutdown = false;
	
	private static HashMap<String,Integer> getInitialToolCounts()
	{
		HashMap<String,Integer> init = new HashMap<>();
		init.put("concrete",1);
		init.put("painting",8);
		init.put("plumbing",5);
		init.put("woodwork",6);
		init.put("yardwork",4);
		return init;
	}
	
	/*
	Start by knowing how many of each type of tool there is
	 */
	private ToolShopInventory(HashMap<String,Integer> toolCounts)
	{
		inventory = new HashMap<>();
		
		SimpleToolFactory stf = new SimpleToolFactory();
		//use the simple tool factory to initialize all of these objects
		Set countKeys = toolCounts.keySet();
		Iterator tcit = countKeys.iterator();
		while(tcit.hasNext())
		{
			String type = (String)tcit.next();
			LinkedList<Tool> nextQ = new LinkedList<Tool>();
			//now create all of the tools of this type
			int numTools = toolCounts.get(type);
			for(int i = 0; i < numTools; i++)
			{
				//create a new tool of this type
				Tool tool = stf.createTool(type);
				nextQ.add(tool);//since we added to the end, we'll need to take items from the beginning
			}
			
			
			//finally, add that queue to the hashmap for the tool inventory
			inventory.put(type,nextQ);
		}
		numToolsCurrentlyInInventory = 24;//we'll change this around whenever a tool gets checked out or in
	}
	
	public static ToolShopInventory getInstance()
	{
		return instance;
	}
	
	@Override
	public Object get(String type)
	{
		Tool ret = inventory.get(type).removeFirst();//get the first tool in the queue of the correct type
		numToolsCurrentlyInInventory--;
		return ret;
	}
	
	@Override
	public void release(Object obj)
	{
		String type = ((Tool) obj).category.getCategory();//now we have the type of tool, add it to the correct queue in the hashmap
		inventory.get(type).add((Tool) obj);
	}
	
	@Override
	public void shutdown()
	{
		Set keys = inventory.keySet();
		Iterator keyit = keys.iterator();
		while(keyit.hasNext())
		{
			LinkedList current = inventory.get(keyit.next());
			current.clear();
		}
		inventory.clear();
		isShutdown = true;
	}
}
