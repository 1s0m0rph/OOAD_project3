package toolshop.test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import toolshop.Tool;
import toolshop.ToolShopInventory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToolShopInventoryTest
{
	
	@org.junit.jupiter.api.Test
	@Order(1)
	void getInstance()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
	}
	
	@org.junit.jupiter.api.Test
	@Order(2)
	void get()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		Tool tool = (Tool)(tsi.get("concrete"));
		assert(tsi.getNumToolsCurrentlyInInventory() == 23);
		assert(tool.getCategory().getCategoryName().equals("concrete"));
		assert(tsi.get("concrete") == null);//there's only one concrete tool
		tsi.release(tool);
	}
	
	@org.junit.jupiter.api.Test
	@Order(3)
	void release()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		Tool tool = (Tool)(tsi.get("concrete"));
		assert(tsi.getNumToolsCurrentlyInInventory() == 23);
		assert(tool.getCategory().getCategoryName().equals("concrete"));
		assert(tsi.get("concrete") == null);//there's only one concrete tool
		tsi.release(tool);//return this tool
		assert(tsi.getNumToolsCurrentlyInInventory() == 24);
		tool = (Tool)(tsi.get("concrete"));
		assert(tsi.getNumToolsCurrentlyInInventory() == 23);
		assert(tool.getCategory().getCategoryName().equals("concrete"));//make sure it's back in the inventory
		tsi.release(tool);
		assert(tsi.getNumToolsCurrentlyInInventory() == 24);
	}
	
	@org.junit.jupiter.api.Test
	@Order(4)
	void shutdown()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		tsi.shutdown();
		try
		{
			tsi.get("plumbing");
		}
		catch(Exception ignored)
		{
			return;
		}
		assert(false);
	}
}