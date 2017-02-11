package authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BuildStaticParameters {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/TeenViolence";
	static final String USER = "root";
	static final String PASS = "changiz";
	public static Connection conn = null;
	public static Statement stmt =null;

	public static void buildConnectionWithSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt=BuildStaticParameters.conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
