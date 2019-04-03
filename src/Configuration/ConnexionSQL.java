package Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionSQL {

	
	
	public static Connection getConnection() throws SQLException, IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("connect.conf");
		props.load(in);
		in.close();

		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
		     System.setProperty("jdbc.drivers",drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		return DriverManager.getConnection(url, username, password);
		}
	
}