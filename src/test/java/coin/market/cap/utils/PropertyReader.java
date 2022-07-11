package coin.market.cap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {

	// To read keys from properties file
	public static String readEnvConfigFile(String key) {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File(GlobalVariables.envConfigFilePath)));

			return properties.getProperty(key);
		} catch (Exception e) {
			return null;
		}
	}

}