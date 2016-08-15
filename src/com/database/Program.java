package com.database;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program
{
	private static Connection conn;
	private static String url = "jdbc:mysql://localhost:3306/data";
	private static String user = "duxcms";
	private static String password = "szpdOwRBK5g3";
	private static String driver = "com.mysql.jdbc.Driver";
	static
	{
		try
		{
			conn = DriverManager.getConnection(url,user,password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		try
		{
			Statement statement1,statement2 = null;
			ResultSet resultSet1,resultSet2 = null;
			statement1 = conn.createStatement();
			statement2 = conn.createStatement();
			resultSet1 = statement1.executeQuery("select * from quyu");
			while(resultSet1.next())
			{
				int id = resultSet1.getInt("id");
				String name = resultSet1.getString("name").replace("省", "").replace("市", "").replace("区", "").replace("县", "");
				int parent_id = resultSet1.getInt("parent_id");
				String summary = resultSet1.getString("summary");
				int level = resultSet1.getInt("level");
				String insert = "insert into diming (id,name,parent_id,summary,level) values (" + id + ",'" + name + "'," + parent_id + ",'" + summary + "'," + level + ")";
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
