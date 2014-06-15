package ggll.core.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
			this.properties = new Properties();
			this.properties.loadFromXML(new FileInputStream(this.propertieFile));
		}
		catch (final IOException e)
		{
			this.properties = null;
		}
	}
	
	public String getJavaSDKPath()
	{
		if (this.properties != null) { return this.properties.getProperty("jdk_path"); }
		return "";
	}
	
	public void setJavaSDKPath(String path)
	{
		try
		{			
			if (this.properties != null)
			{
				this.properties.setProperty("jdk_path", path);
				File f = new File(this.propertieFile);
				OutputStream out = new FileOutputStream(f);
				this.properties.storeToXML(out, null, "ISO-8859-1");
			}
		}
		catch (Exception e)
		{
		}
	}
}
