package com.restful;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.DatabaseConfig;
import com.pojo.ModelItem;

@Path("/check")
public class CheckDatabase
{
	private static QueryRunner runner;
	private static Connection conn;
	static
	{
		try
		{
			Class.forName(DatabaseConfig.getDriver());
			runner = new QueryRunner();
			conn = DatabaseConfig.getConnection();
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@POST
	@Produces({MediaType.TEXT_PLAIN})
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/check_code_and_name")
	public long checkItem(ModelItem modelItem)
	{
		String query = "select count(id) from model_item where name=? and code=?";
		try
		{
			Long count = runner.query(conn, query, new ScalarHandler<Long>(),modelItem.getName(),modelItem.getCode());
			System.out.println(count);
			return count;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/check_name")
	public long checkName(ModelItem modelItem)
	{
		String sql = "select count(id) from model_item where name=?";
		try
		{
			long count = runner.query(conn, sql, new ScalarHandler<Long>(),modelItem.getName());
			return count;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
	}
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/check_code")
	public long checkCode(ModelItem modelItem)
	{
		String sql = "select count(id) from model_item where code=?";
		try
		{
			long count = runner.query(conn, sql, new ScalarHandler<Long>(),modelItem.getCode());
			return count;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
	}
	
}
