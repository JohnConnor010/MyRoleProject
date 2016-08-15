package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.management.RuntimeErrorException;



public class DatabaseConfig
{
	private static Properties config = new Properties();
	static
	{
		try
		{
			config.load(DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties"));
			Class.forName(config.getProperty("driver"));
		}
		catch (Exception e)
		{
			throw new ExceptionInInitializerError();
		}
	}
	public static Connection getConnection()
	{
		Connection connection = null;
		try
		{
			String url = config.getProperty("url");
			String user = config.getProperty("user");
			String password = config.getProperty("password");
			connection = DriverManager.getConnection(url,user,password);
		}
		catch (Exception e)
		{
			throw new RuntimeErrorException(null, "连接数据库出错");
		}
		return connection;
	}
	public static void release(Connection connection,Statement statement,ResultSet resultSet)
	{
		if(resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch (Exception e)
			{
				// TODO: handle exception
				throw new RuntimeErrorException(null, "关闭结果集出错");
			}
		}
		if(statement != null)
		{
			try
			{
				statement.close();
			}
			catch (Exception e)
			{
				// TODO: handle exception
				throw new RuntimeErrorException(null, "Statement关闭失败");
			}
		}
		if(connection != null)
		{
			try
			{
				connection.close();
			}
			catch (Exception e)
			{
				throw new RuntimeErrorException(null, "Connection关闭失败");
			}
		}
	}
	public static String getDriver()
	{
		return config.getProperty("driver");
	}
}
