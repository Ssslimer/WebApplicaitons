package task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LineCounter
{
	protected final List<String> pathsToFiles;
	
	public LineCounter(String[] fileNames)
	{
		this.pathsToFiles = new ArrayList<>(Arrays.asList(fileNames));
	}
	
	public abstract int countLines();
}
