package task1;

import java.util.Arrays;

public class Task1
{
	public enum Mode
	{
		SERIAL, PARALLEL;
	}
	
	public static void main(String[] args)
	{
		if(areArgsWrong(args)) return;
		
		Mode mode = parseMode(args[0]);
		
		String[] filePaths = Arrays.copyOfRange(args, 1, args.length);
		
		countLines(mode, filePaths);
	}
	
	public static boolean areArgsWrong(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("Wrong arguments. Try: <mode> <file names ...>");
			return true;
		}
		
		if(!(args[0].equals("serial") || args[0].equals("parallel")))
		{
			System.out.println("Wrong application mode. It can be serial or parallel");
			return true;
		}
		
		return false;
	}
	
	public static Mode parseMode(String mode)
	{
		if(mode.equalsIgnoreCase("serial")) return Mode.SERIAL;
		else return Mode.PARALLEL;
	}
	
	public static void countLines(Mode mode, String[] fileNames)
	{
		if(mode == Mode.SERIAL)
		{
			LineCounter lineCounter = new SerialLineCounter(fileNames);
			
			long t1 = System.nanoTime();
			int lineCount = lineCounter.countLines();
			long t2 = System.nanoTime();
			
			System.out.println("Time for serial mode = " + (t2-t1)/1000000f + "ms");
			System.out.println("Number of lines = " + lineCount);
		}
		else if(mode == Mode.PARALLEL)
		{
			LineCounter lineCounter = new ParallelLineCounter(fileNames);
			
			long t1 = System.nanoTime();
			int lineCount = lineCounter.countLines();
			long t2 = System.nanoTime();
			
			System.out.println("Time for parallel mode = " + (t2-t1)/1000000f + "ms");
			System.out.println("Number of lines = " + lineCount);
		}
	}
}
