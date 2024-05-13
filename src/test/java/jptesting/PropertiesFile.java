package jptesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {
	
	public String readProperties(String key) {
		File config = new File("src/test/resources/Config.properties");
		
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(config)) {
			prop.load(input);
		} catch (Exception e) {
			System.out.println("Caught: " + e.getMessage());
		}
		
		return prop.getProperty(key);
	}
}
