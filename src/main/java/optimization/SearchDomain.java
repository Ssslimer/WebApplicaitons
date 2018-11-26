package optimization;

public class SearchDomain
{
	double[][] ranges;
	
	public SearchDomain(double... ranges)
	{
		this.ranges = new double[ranges.length/2][2];
		
		for(int i = 0; i < ranges.length; i+=2)
		{
			this.ranges[i/2][0] = ranges[i];
			this.ranges[i/2][1] = ranges[i+1];
		}
	}
}
