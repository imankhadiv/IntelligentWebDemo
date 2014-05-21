package twitterVenueSearch;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Models.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MyDB {

	private String userName = "team019";
	private String password = "077cea79";
	private String url = "jdbc:mysql://127.0.0.1:3306/test";
	String DB="jdbc:mysql://stusql.dcs.shef.ac.uk/team019?user=team019&password=077cea79";

	private Connection connection;

	public MyDB() {
		Statement stm = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = (Connection) DriverManager.getConnection(url);
			if (connection != null)
				System.out.println("Database connection established");
			// create table
			stm = (Statement) connection.createStatement();
			String sqlString = "CREATE TABLE IF NOT EXISTS new_table ("
					+ "id INT NOT NULL AUTO_INCREMENT," + "name TEXT NULL,"
					+ "scrname TEXT NULL," + "location TEXT NULL,"
					+ "decription TEXT NULL," + "picURL TEXT NULL,"
					+ "PRIMARY KEY (id)," + "UNIQUE INDEX id_UNIQUE (id ASC));";
			stm.executeUpdate(sqlString);
			stm.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String filter(String org) {
		String string = "";
		if (org != null && org != "") {
			string = org.replace("'", "");
		}
		return string;
	}
	
	public void close() throws SQLException
	{
		connection.close();
	}

	public void insert(String name, String screen_name, String location,
			String description, String picURL) throws SQLException {
		name = filter(name);
		screen_name = filter(screen_name);
		location = filter(location);
		description = filter(description);
		picURL = filter(picURL);

		Statement smtStatement = (Statement) connection.createStatement();
		String sqlString = "INSERT INTO new_table (name,scrname,location,decription,picURL) values ('"
				+ name
				+ "','"
				+ screen_name
				+ "','"
				+ location
				+ "','"
				+ description + "','" + picURL + "')";
		// System.out.println(sqlString);
		smtStatement.execute(sqlString);
		smtStatement.close();
	}

	public String setString(String input) {
		String ret = "";
		if (input != null)
			ret = input;
		return ret;
	}

	public List<User> selectall() throws SQLException {
		List<User> userlist = new ArrayList<User>();
		Statement s = (Statement) connection.createStatement();
		s.executeQuery("SELECT * FROM new_table");
		ResultSet rs = s.getResultSet();
		int count = 0;
		while (rs.next()) {
			int idVal = rs.getInt("id");

			String nameVal = setString(rs.getString("name"));
			String scrnameVal = setString(rs.getString("scrname"));
			String locationVal = setString(rs.getString("location"));
			String decriptionVal = setString(rs.getString("decription"));
			String picURLVal = setString(rs.getString("picURL"));

			User user = new User(nameVal, scrnameVal, locationVal,
					decriptionVal, picURLVal);
			userlist.add(user);
			System.out.println("id = " + idVal + ", name = " + nameVal
					+ ", screen_name = " + scrnameVal);
			++count;
		}
		rs.close();
		s.close();
		System.out.println(count + " rows were retrieved");
		return userlist;
	}

	public void droptable(String tablename) throws SQLException {
		Statement s = (Statement) connection.createStatement();
		s.executeUpdate("DROP TABLE IF EXISTS " + tablename);
		s.close();
	}

	public static void main(String[] args) throws SQLException {
		MyDB myDB = new MyDB();
		// myDB.insert("njy", "njy@123.com");
		// myDB.selectall();
		myDB.droptable("new_table");
		// myDB.selectall();
	}

}
