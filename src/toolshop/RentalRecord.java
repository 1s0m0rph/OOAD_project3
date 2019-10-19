package toolshop;

import toolshop.Customer;

import java.util.ArrayList;

public class RentalRecord
{
	private Customer renter;
	private ArrayList<Purchasable> toolsRented;
	private int dayRented;
	private int totalRentalTime;
	private int dayDue;
	
	public RentalRecord(Customer renter, ArrayList<Purchasable> toolsRented, int dayRented, int totalRentalTime)
	{
		this.renter = renter;
		this.toolsRented = toolsRented;
		this.dayRented = dayRented;
		this.totalRentalTime = totalRentalTime;

		dayDue = dayRented + totalRentalTime;
	}

	public void returnTools()
	{
		renter.returnTools(toolsRented.size());
		ToolShopInventory tsi = ToolShopInventory.getInstance();
		for (Purchasable t : toolsRented)
		{
			tsi.release(t);
		}
	}

	public Customer getRenter()
	{
		return renter;
	}

	public ArrayList<Purchasable> getToolsRented()
	{
		return toolsRented;
	}

	public int getDayRented()
	{
		return dayRented;
	}

	public int getTotalRentalTime()
	{
		return totalRentalTime;
	}

	public int getDueDate()
	{
		return dayDue;
	}

	public String toString()
	{
		StringBuilder retString  = new StringBuilder();
		retString.append(renter.getName()).append("\n");
		retString.append(toolsRented.size()).append(" tool").append((toolsRented.size() == 1) ? "" : "s").append(" rented for ").append(totalRentalTime).append(" days on day ").append(dayRented).append("\n");
		int cost = 0;
		for (int i=0; i < toolsRented.size(); i++)
		{
			Purchasable p = toolsRented.get(i);
			cost += p.getCost();
			ArrayList<PurchaseDecorator> options = p.getOptions();
			Tool tool = p.getTool();
			retString.append(tool.getCategory().getCategoryName()).append(" tool with: ");
			for (int j=0; j < options.size(); j++)
			{
				retString.append(options.get(j).getType());
				if (j != options.size()-1)
					retString.append(", ");
				else
					retString.append("\n");
			}
		}
		retString.append("Total: $").append(cost).append("\n");
		return retString.toString();
	}
}
