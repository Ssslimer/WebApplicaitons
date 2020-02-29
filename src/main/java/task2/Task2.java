package task2;

import java.io.IOException;
import java.net.MalformedURLException;

public class Task2
{
	public static void main(String[] args) throws MalformedURLException, IOException
	{
		String websiteAddress = args[0];
		
		Website website = new Website(websiteAddress);
		website.saveDataToFile();
	}
}
