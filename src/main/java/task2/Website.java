package task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Website
{
	private static final Pattern HYPERLINKS_PATTERN = Pattern.compile("href=\"(.*?)\"");
	
	/* Regex taken from: https://howtodoinjava.com/regex/java-regex-validate-email-address/
	it is specified for currently used standard RFC 5322 */
	private static final Pattern EMAIL_PATTERN = Pattern.compile("[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}");	
	private static final Pattern HEAD_PATTERN = Pattern.compile("<head>(.*)</head>", Pattern.DOTALL);
			
	private URLConnection urlConnection;
	
	public Website(String websiteAddress) throws MalformedURLException, IOException
	{
	    URL url = new URL(websiteAddress);
	    this.urlConnection = url.openConnection();
	    this.urlConnection.connect();
	}
		
	public void saveDataToFile(String outputFilename) throws IOException
	{
		String ip = "IP: " + getIP();		
		String content = loadContent();
		
		List<String> parameters = getConnectionParameters();
		List<String> emails = findEmailAddresses(content);		
		List<String> hyperlinks = findHyperlinks(content);
		
		File outputFile = new File(outputFilename);
		
		if(!outputFile.exists()) outputFile.createNewFile();
		
		try(PrintWriter out = new PrintWriter(outputFile))
		{
			System.out.println("Writing to: " + outputFile.getAbsolutePath());
			out.println(ip);
			
			out.println("Connection parameters:");
			for(String s : parameters) out.println(s);		
			out.println(" ");
			
			out.println(findHead(content));
			
			if(emails.isEmpty()) out.println("\nNo emails found.");
			else
			{
				out.println("\\nEmails found:");
				for(String s : emails) out.println(s);
			}
			
			if(hyperlinks.isEmpty()) out.println("\nNo hyperlinks found.");
			else
			{
				out.println("\nHyperlinks found:");
				for(String s : hyperlinks) out.println(s);
			}
		}		
	}
	
	public String getIP() throws UnknownHostException
	{
	    String host = urlConnection.getURL().getHost();    
	    InetAddress address = InetAddress.getByName(host);
	    
	    return address.getHostAddress(); 
	}
	
	private String loadContent() throws UnsupportedEncodingException, IOException
	{	
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")))
		{
			String inputLine;
			StringBuilder stringBuilder = new StringBuilder();
			
			while((inputLine = bufferedReader.readLine()) != null)
			{
				stringBuilder.append(inputLine);
				stringBuilder.append('\n');
	        }
	
			return stringBuilder.toString();
		}
	}
	
	private List<String> getConnectionParameters()
	{
		List<String> connectionParameters = new LinkedList<>();
		
		Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
		for(Entry<String, List<String>> entry : headerFields.entrySet())
		{
			connectionParameters.add(entry.getKey() +" "+entry.getValue());
		}
		
		return connectionParameters;
	}
	
	private String findHead(String content)
	{
		if(content == null) return null;
		
		Matcher matcher = HEAD_PATTERN.matcher(content);
		
		if(matcher.find()) return matcher.group();
		else return "No <head> found";
	}
	
	private List<String> findEmailAddresses(String content)
	{
		if(content == null) return Collections.emptyList();
		
		Matcher matcher = EMAIL_PATTERN.matcher(content);
		
		List<String> emails = new LinkedList<>();		
		while(matcher.find())
		{
			emails.add(matcher.group());
		}
		
		return emails;
	}

	private List<String> findHyperlinks(String content)
	{
		if(content == null) return Collections.emptyList();

		Matcher matcher = HYPERLINKS_PATTERN.matcher(content);
		
		List<String> hyperLinks = new LinkedList<>();		
		while(matcher.find())
		{
			hyperLinks.add(matcher.group());
		}
		
		return hyperLinks;
	}

}
