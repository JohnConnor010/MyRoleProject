package com.restful;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.DatabaseConfig;

@Path("/cascading")
public class CascadingService
{
	private static Connection conn;
	private static QueryRunner runner;
	static
	{
		DbUtils.loadDriver(DatabaseConfig.getDriver());
		conn = DatabaseConfig.getConnection();
		runner = new QueryRunner();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findById/{id}/{itemId}")
	public List<Map<String, Object>> findById(@PathParam("id") int id,@PathParam("itemId") int itemId) throws SQLException
	{
		String query = "select code from model_item where id=?";
		String code = runner.query(conn, query, new ScalarHandler<String>(),itemId);
		if(code != null)
		{
			String sql = "select id,name from " + code + " where parent_id=?";
			List<Map<String, Object>> maps = runner.query(conn, sql, new MapListHandler(),id);
			return maps;
		}
		else
		{
			return null;
		}
		
	}
	
}
