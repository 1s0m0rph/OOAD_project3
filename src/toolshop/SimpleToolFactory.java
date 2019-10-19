package toolshop;

public class SimpleToolFactory
{
	public Tool createTool(String type)
	{
		Tool ret;
		if(type.equalsIgnoreCase("concrete"))
			ret = new Tool(new Concrete());
		else if(type.equalsIgnoreCase("painting"))
			ret = new Tool(new Painting());
		else if(type.equalsIgnoreCase("plumbing"))
			ret = new Tool(new Plumbing());
		else if(type.equalsIgnoreCase("woodwork"))
			ret = new Tool(new Woodwork());
		else if(type.equalsIgnoreCase("yardwork"))
			ret = new Tool(new Yardwork());
		else
			throw new IllegalArgumentException(type + " is not a valid tool type");
		
		return ret;
	}
}
