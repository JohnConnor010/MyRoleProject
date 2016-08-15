package com.restful;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.DatabaseConfig;

@Path("/delete_item")
public class DeleteItemService
{
	private static Connection conn;
	private static QueryRunner runner;
	static
	{
		try
		{
			DbUtils.loadDriver(DatabaseConfig.getDriver());
			conn = DatabaseConfig.getConnection();
			runner = new QueryRunner();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	@Path("{id}")
	public String deleteItem(@PathParam("id") int id)
	{		
		try
		{
			String code = runner.query(conn, "select code from model_item where id=?", new ScalarHandler<String>(),id);
			if(code != null)
			{
				int state1 = runner.update(conn, "DROP TABLE `data`.`" + code + "`;");				
			}
			String delete = "delete from model_item where id=?";
			int states = runner.update(conn, delete, id);
			return "OK";
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return "Wrong";
		}
	}
}
