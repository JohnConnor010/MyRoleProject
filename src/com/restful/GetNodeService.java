package com.restful;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.DatabaseConfig;

@Path("/tree")
public class GetNodeService
{
	private static Connection conn;
	private static QueryRunner runner;
	
	static
	{
		DbUtils.loadDriver(DatabaseConfig.getDriver());
		conn = DatabaseConfig.getConnection();
		runner = new QueryRunner();
	}
	@POST
	@Path("/get_data")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getData(String param,@QueryParam("category_id") int category_id)
	{
		
		List<Map<String, Object>> results = new ArrayList<>();
		int id = 0;
		if(!param.equals(""))
		{
			id = Integer.valueOf(param.split("=")[1]);
		}
		String query = "select * from organization where parent_id=? and category_id=?";
		try
		{
			List<Map<String, Object>> maps = runner.query(conn, query, new MapListHandler(),id,category_id);
			
			for (Map<String, Object> map : maps)
			{
				Map<String, Object> map1 = new HashMap<>();
				map1.put("id", map.get("id"));
				map1.put("text", map.get("name"));
				map1.put("state", hasChild(map.get("id")) == true ? "closed" : "open");
				results.add(map1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return results;
	}
	public boolean hasChild(Object id)
	{
		boolean result = false;
		String query = "select count(id) from organization where parent_id=?";
		try
		{
			long count = runner.query(conn, query,new ScalarHandler<Long>(),id);
			if(count > 0)
			{
				result = true;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			result = false;
		}
		return result;
	}
	
}
