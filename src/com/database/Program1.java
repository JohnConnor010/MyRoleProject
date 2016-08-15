package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program1
{
	private static Connection conn;
	private static String url = "jdbc:mysql://localhost:3306/data";
	private static String user = "root";
	private static String password = "123456789";
	private static String driver = "com.mysql.jdbc.Driver";
	static
	{
		try
		{
			conn = DriverManager.getConnection(url, user, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		Statement statement1,statement2;
		ResultSet resultSet1;
		try
		{
			statement1 = conn.createStatement();
			statement2 = conn.createStatement();
			String query = "select * from vocation where ParentID=0";
			resultSet1 = statement1.executeQuery(query);
			while(resultSet1.next())
			{
				String name = resultSet1.getString("Name");
				int parent_id = 0;
				String summary = resultSet1.getString("summary");
				String insert = "insert into industry (name,parent_id,summary) values ('" + name + "'," + parent_id + ",'" + summary + "')";
				statement2.execute(insert);
				System.out.println(insert);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
