package ggll.core.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GGLLProperties
{
	private Properties properties;
	private String propertieFile = "ggll.properties";

	public GGLLProperties()
	{
		loadProperties();
	}

	public GGLLProperties(String propertieFile)
	{
		this.propertieFile = propertieFile;
		loadProperties();
	}

	private void loadProperties()
	{
		try
		{
			properties = new Properties();
			properties.loadFromXML(new FileInputStream(propertieFile));
		}
		catch (IOException e)
		{
			properties = null;
		}
	}

	public String getJavaSDKPath()
	{
		if (properties != null)
		{
			return properties.getProperty("jdk_path");
		}
		return "";
	}
}
