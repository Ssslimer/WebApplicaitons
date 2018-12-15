package optimization;

import java.io.Serializable;

public class SearchDomain implements Serializable
{
	private static final long serialVersionUID = -4764226623783875267L;
	
	public double[][] ranges;
	
	public SearchDomain(double... ranges)
	{
		this.ranges = new double[ranges.length/2][2];
		
		for(int i = 0; i < ranges.length; i+=2)
		{
			this.ranges[i/2][0] = ranges[i];
			this.ranges[i/2][1] = ranges[i+1];
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		b.append("SearchDomain:");
		
		for(int i = 0; i < ranges.length; i++)
		{
			for(int j = 0; j < ranges[0].length; j++)
			{
				b.append(ranges[i][j]);
				b.append(' ');
			}	
		}
		
		return b.toString();
	}
}
