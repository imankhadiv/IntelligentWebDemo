package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import beans.Person;

public class Data {
	Connection con;

	public Data(Connection con) {
		this.con = con;
	}

	public void storeUser(Person person) throws SQLException {
		if (!checkUser(person.getScreenName())) {
			String sql = "insert into people(name, screen_name,location,picture) values(?, ?, ?, ?) ";
			//String sql2 ="SELECT IDENT_CURRENT('people')";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, person.getName());
			stmt.setString(2, person.getScreenName());
			if (person.getLocation() != null)
				stmt.setString(3, person.getName());
			else
				stmt.setString(3, "N/A");
			stmt.setString(4, person.getProfilePicture());
			//PreparedStatement stmt2 = con.prepareStatement(sql2);
			stmt.execute();
//			int i = stmt2.executeUpdate();
			stmt.close();
			///stmt2.close();
		}
	}

	public void storeKeywords(Person person) throws SQLException {

		con.setAutoCommit(false);
		Statement stst = con.createStatement();
		Map<String, Integer> map = person.getMap();
		if (map != null) {
			for (String item : map.keySet()) {

				if (checkKeyword(item)) {
					stst.addBatch("insert into keywords(keyword) values(item)");
				}

			}
		}
		stst.executeBatch();
		con.commit();

	}

	private boolean checkUser(String name) throws SQLException {
		String sql = "select count(*) as count from people where screen_name=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();

		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count");
		}
		rs.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	private boolean checkKeyword(String word) throws SQLException {
		String sql = "select count(*) as count from keywords where keyword=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, word);
		ResultSet rs = stmt.executeQuery();

		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count");
		}
		rs.close();
		if (count == 0) {
			return false;
		} else {
			return true;
		}

	}

	public ResultSet getUsers() throws SQLException {
		Statement stst = con.createStatement();
		ResultSet resultSet = stst.executeQuery("select * from people");
		
		return resultSet;

	}

	public ResultSet getKeywords() throws SQLException {
		Statement stst = con.createStatement();
		ResultSet resultSet = stst.executeQuery("select * from keywords");
		return resultSet;

	}
}
