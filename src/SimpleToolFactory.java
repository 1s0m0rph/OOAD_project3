public class SimpleToolFactory
{
	public Tool createTool(String type)
	{
		Tool ret = new Tool();
		if(type.equalsIgnoreCase("concrete"))
			ret.setCategory(new Concrete());
		else if(type.equalsIgnoreCase("painting"))
			ret.setCategory(new Painting());
		else if(type.equalsIgnoreCase("plumbing"))
			ret.setCategory(new Plumbing());
		else if(type.equalsIgnoreCase("woodwork"))
			ret.setCategory(new Woodwork());
		else if(type.equalsIgnoreCase("yardwork"))
			ret.setCategory(new Yardwork());
		else
			throw new IllegalArgumentException(type + " is not a valid tool type");
		
		return ret;
	}
}
