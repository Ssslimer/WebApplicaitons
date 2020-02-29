package task2;

import java.io.IOException;

public class Task2
{
	public static void main(String[] args)
	{
		String websiteAddress = args[0];
		
		try
		{
			Website website = new Website(websiteAddress);
			website.saveDataToFile("website_data.txt");
		}
		catch(IOException e)
		{
			System.out.println("The process failed");
			System.out.println(e.getMessage());
		}

	}
}
