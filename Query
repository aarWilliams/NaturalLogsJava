package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

	public static boolean queryExist(Connection con, Lumber l) throws SQLException
	{
		boolean exists = true;

		Statement stmt = con.createStatement();
		try{
			String sql = "SELECT name FROM WOODSTACK WHERE name = " + "\"" + l.getHeight() + "x"
						 + l.getWidth() + "x" + l.getLength() + "\"" + ";";
			ResultSet res = stmt.executeQuery(sql);
			if(!res.next())
			{
				exists = false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		stmt.close();
		return exists;
	}

	public static String querySelect(Connection con, Lumber l) throws SQLException
	{
		String result = "";
		Statement stmt = con.createStatement();
		try{
			String sql = "SELECT name, quantity, value FROM WOODSTACK WHERE name = " + "\"" + l.getHeight() + "x"
					 + l.getWidth() + "x" + l.getLength() + "\"" + ";";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				result += "name: " + rs.getString("name") + "\n" + "quantity: " + rs.getDouble("quantity") + "\n"
				+ "value: " + rs.getDouble("value") + "\n";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		stmt.close();
		return result;
	}

	public static void queryUpdate(Connection con, Lumber l, int count, double countArray[], double scrap, double scrapEcon) throws SQLException
	{
		Statement stmt = con.createStatement();
		double quantity = countArray[count];
		String sql = "Update WOODSTACK SET quantity = quantity + " + quantity + ", value =  " + l.getValue() + "WHERE name = " + "\""
					 + l.getHeight() + "x" + l.getWidth() + "x" + l.getLength() + "\"" + ";";
		stmt.execute(sql);

		if(count == 1)
		{
			sql = "Update SCRAP SET volume = volume + " + scrap + ", value = " + scrapEcon + ";";
			stmt.execute(sql);
		}
		stmt.close();
	}

	public static void queryInsert(Connection con, Lumber l, int count, double countArray[], double scrap, double scrapEcon) throws SQLException
	{
		Statement stmt = con.createStatement();
		double quantity = countArray[count];
		String sql = "INSERT INTO WOODSTACK (name, quantity, height, width, length, value) VALUES "
					 + " (" + "\"" + l.getHeight() + "x" + l.getWidth() + "x" + l.getLength() + "\"" + ", "
					 + quantity + ", " + l.getHeight() + ", " + l.getWidth() + ", " + l.getLength() + ", "
					 + l.getValue() + ");";
		stmt.execute(sql);

		if(count == 1)
		{
			sql = "Update SCRAP SET volume = volume + " + scrap + ", value = " + scrapEcon + ";";
			stmt.execute(sql);
		}
		stmt.close();
	}
}
