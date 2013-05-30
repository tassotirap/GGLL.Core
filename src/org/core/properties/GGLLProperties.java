package org.core.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GGLLProperties
{
	private Properties properties;
	
	public GGLLProperties()
	{
		loadProperties();
	}
	
	public String getJavaSDKPath()
	{
		return properties.getProperty("jdk_path");
	}
	
	public void loadProperties()
	{
		try
		{					
			properties = new Properties();
			properties.loadFromXML(new FileInputStream("ggll.properties"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
