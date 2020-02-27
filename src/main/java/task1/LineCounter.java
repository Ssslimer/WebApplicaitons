import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LineCounter
{
	protected List<String> fileNames;
	
	public LineCounter(String[] fileNames)
	{
		this.fileNames = new ArrayList<>(Arrays.asList(fileNames));
	}
	
	public abstract int countLines();
}
