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
	private URL url;
	private URLConnection urlConnection;
	private String content;
	
	public Website(String websiteAddress) throws MalformedURLException, IOException
	{
	    this.url = new URL(websiteAddress);
	    this.urlConnection = url.openConnection();
	    this.urlConnection.connect();
	}
	
	public String getIP() throws UnknownHostException
	{
	    String host = url.getHost();    
	    InetAddress address = InetAddress.getByName(host);
	    
	    return address.getHostAddress(); 
	}
	
	private void loadContent() throws UnsupportedEncodingException, IOException
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

			content = stringBuilder.toString();
		}
	}
	
	/* Regex taken from: https://howtodoinjava.com/regex/java-regex-validate-email-address/
		it is specified for currently used standard RFC 5322 */
	public List<String> findEmailAddresses()
	{
		Pattern pattern = Pattern.compile("[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}");
		if(content == null)
		{
			try {loadContent();}
			catch(Exception e) {return Collections.emptyList();}
		}
		Matcher matcher = pattern.matcher(content);
		
		List<String> emails = new LinkedList<>();		
		while(matcher.find())
		{
			emails.add(matcher.group());
		}
		
		return emails;
	}
	
	public List<String> findHyperlinks()
	{
		Pattern pattern = Pattern.compile("href=\"(.*?)\"");	
		if(content == null)
		{
			try {loadContent();}
			catch(Exception e) {return Collections.emptyList();}
		}
		Matcher matcher = pattern.matcher(content);
		
		List<String> hyperLinks = new LinkedList<>();		
		while(matcher.find())
		{
			hyperLinks.add(matcher.group());
		}
		
		return hyperLinks;
	}
	
	public void saveDataToFile() throws IOException
	{
		String ip;
		try {ip = "IP: " + getIP();}
		catch(UnknownHostException e) {ip = "Cannot determine IP address.";}

		List<String> parameters = getConnectionParameters();
		List<String> emails = findEmailAddresses();		
		List<String> hyperlinks = findHyperlinks();
		
		File file = new File("website_data.txt");
		if(!file.exists()) file.createNewFile();
		try(PrintWriter out = new PrintWriter(file))
		{
			System.out.println("Writing to: " + file.getAbsolutePath());
			out.println(ip);
			out.println("Connection parameters:");
			for(String s : parameters) out.println(s);
			out.println(" ");
			out.println(findHead());
			
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
	
	public List<String> getConnectionParameters()
	{
		List<String> connectionParameters = new LinkedList<>();
		
		Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
		for(Entry<String, List<String>> entry : headerFields.entrySet())
		{
			connectionParameters.add(entry.getKey() +" "+entry.getValue());
		}
		
		return connectionParameters;
	}
	
	public String findHead()
	{
		Pattern pattern = Pattern.compile("<head>(.*)</head>", Pattern.DOTALL);
		if(content == null)
		{
			try {loadContent();}
			catch(Exception e) {return "No <head> found";}
		}
		
		Matcher matcher = pattern.matcher(content);	
		if(matcher.find()) return matcher.group();
		else return "No <head> found";
	}
	
	public String getContent()
	{
		return content;
	}
}
