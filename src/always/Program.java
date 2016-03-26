package always;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.UIManager;

public class Program {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			InputStream input = new FileInputStream("always.properties");
	        Properties properties = new Properties();
	        properties.load(input);
	        System.out.println(properties);
	        input.close();
	        
	        AlwaysClient.getInstance().init(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
