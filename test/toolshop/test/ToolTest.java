package toolshop.test;

import org.junit.jupiter.api.Test;
import toolshop.*;

import java.util.ArrayList;

class ToolTest
{
	
	@Test
	void getCost()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		Purchasable tool = (Tool) tsi.get("painting");
		tool.setTimeOfRental(10);
		
		tool = new AccessoryKit(tool);
		tool = new ExtensionCord(tool);
		tool = new ProtectiveGearPackage(tool);
		
		assert(tool.getCost() == (10 * (10 + 20) + 5 + 2 + 1));
		tsi.release(tool);
	}
	
	@Test
	void getOptions()
	{
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		Purchasable tool = (Tool) tsi.get("painting");
		tool.setTimeOfRental(10);
		
		tool = new AccessoryKit(tool);
		tool = new ExtensionCord(tool);
		tool = new ProtectiveGearPackage(tool);
		
		ArrayList<PurchaseDecorator> opts = tool.getOptions();
		
		assert(opts.get(0) instanceof AccessoryKit);
		assert(opts.get(1) instanceof ExtensionCord);
		assert(opts.get(2) instanceof ProtectiveGearPackage);
		assert(tool.getTool().getCategory() instanceof Painting);
		tsi.release(tool);
	}
}