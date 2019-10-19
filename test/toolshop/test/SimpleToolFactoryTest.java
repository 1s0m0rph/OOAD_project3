package toolshop.test;

import org.junit.jupiter.api.Test;
import toolshop.*;

class SimpleToolFactoryTest
{
	
	@Test
	void createTool()
	{
		SimpleToolFactory stf = new SimpleToolFactory();
		Tool t0 = stf.createTool("concrete");
		assert(t0.getCategory() instanceof Concrete);
		Tool t1 = stf.createTool("painting");
		assert(t1.getCategory() instanceof Painting);
		Tool t2 = stf.createTool("plumbing");
		assert(t2.getCategory() instanceof Plumbing);
		Tool t3 = stf.createTool("woodwork");
		assert(t3.getCategory() instanceof Woodwork);
		Tool t4 = stf.createTool("yardwork");
		assert(t4.getCategory() instanceof Yardwork);
		
		//error checking
		try
		{
			stf.createTool("this category does not exist");
			assert(false);
		}
		catch (Exception ignored){}
	}
}