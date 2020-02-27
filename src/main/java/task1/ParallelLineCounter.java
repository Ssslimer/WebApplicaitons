import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ParallelLineCounter extends LineCounter
{
	public ParallelLineCounter(String[] arrayList)
	{
		super(arrayList);
	}
	
	@Override
	public int countLines()
	{
		int linesCount = 0;
		
		List<CountLinesThread> countlinesTasks = new LinkedList<>();
		
		for(String fileName : fileNames)
		{
			Path path = Paths.get(fileName);
			System.out.println("Reading file: " + path.toAbsolutePath());
			
			CountLinesThread thread = new CountLinesThread(path);
			thread.start();
			countlinesTasks.add(thread);
		}
		
		for(CountLinesThread task : countlinesTasks)
		{
			try
			{
				task.join();
				linesCount += task.getLines();
			}
			catch(InterruptedException e) {}
		}
		
		return linesCount;
	}

	private class CountLinesThread extends Thread
	{
		private int linesCount;
		private Path filePath;
		
		public CountLinesThread(Path filePath)
		{
			this.filePath = filePath;
		}
		
		@Override
		public void run()
		{	
			try
			{
				linesCount = Files.readAllLines(filePath).size();
				System.out.printf("%s has %d lines\n", filePath.toAbsolutePath().getFileName(), linesCount);
			}
			catch(IOException e)
			{
				System.out.println("Error while reading file: " + filePath.toAbsolutePath().getFileName());
			}
		}
		
		public int getLines()
		{
			return linesCount;
		}
	}
}
