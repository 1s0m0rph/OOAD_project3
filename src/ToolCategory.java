public class ToolCategory
{
	private String category;
	private int cost;
	
	public String getCategoryName()
	{
		return category;
	}
	
	public ToolCategory(String category, int cost)
	{
		this.category = category;
		this.cost = cost;
	}
	
	public int getPrice()
	{
		return cost;
	}
}
