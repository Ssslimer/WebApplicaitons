import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerialLineCounter extends LineCounter
{
	public SerialLineCounter(String[] arrayList)
	{
		super(arrayList);
	}
	
	@Override
	public int countLines()
	{
		int linesCount = 0;
		
		for(String fileName : fileNames)
		{
			Path path = Paths.get(fileName);
			
			if(path.toFile().isDirectory())
			{
				System.out.printf("File: %s is a directory\n", path.toAbsolutePath().getFileName());
				continue;
			}

			System.out.println("Reading file: " + path.toAbsolutePath());
			
			try
			{
				int count = Files.readAllLines(path).size();
				linesCount += count;
				System.out.printf("%s has %d lines\n", path.toAbsolutePath().getFileName(), count);
			}
			catch(MalformedInputException e) {}
			catch(IOException e)
			{
				System.out.println("Error while reading file: " + path.toAbsolutePath().getFileName());
				System.out.println(e.getMessage());
			}
		}
		
		return linesCount;
	}
}
