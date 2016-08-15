package com.restful;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.DatabaseConfig;
import com.pojo.Keyword;

@Path("/keyword")
public class KeywordService
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("save")
	public Keyword save(Keyword keyword)
	{
		Keyword myKeyword = new Keyword();
		String name = keyword.getName();
		String summary = keyword.getSummary();
		int parentId = keyword.getParent_id();
		int itemId = keyword.getItemId();
		String query1 = "select code from model_item where id=?";
		try
		{
			String code = runner.query(conn, query1, new ScalarHandler<String>(),itemId);
			if(code != null)
			{
				String insert = "insert into " + code + " (name,parent_id,summary) values (?,?,?);";
				int insertState = runner.update(conn, insert, name,parentId,summary);
				if(insertState > 0)
				{
					String select_last_id = "SELECT last_insert_id();";
					BigInteger last_id = runner.query(conn, select_last_id, new ScalarHandler<BigInteger>());
					myKeyword.setId(last_id);
					myKeyword.setName(name);					
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myKeyword;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("find/{id}/{itemId}")
	public Map<String, Object> find(@PathParam("id") int id,@PathParam("itemId") int itemId)
	{
		try
		{
			String sql1 = "select code from model_item where id=?";
			String code = runner.query(conn, sql1, new ScalarHandler<String>(),itemId);
			if(code != null)
			{
				String sql2 = "select * from " + code + " where id=?";
				Map<String, Object> result = runner.query(conn, sql2, new MapHandler(),id);
				return result;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}
	@POST
	@Path("update")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public int update(Keyword keyword)
	{
		try
		{
			String sql1 = "select code from model_item where id=?";
			String code = runner.query(conn, sql1, new ScalarHandler<String>(),keyword.getItemId());
			if(code != null)
			{
				String updateSql = "update " + code + " set name=?,summary=? where id=?";
				int updateStatus = runner.update(conn, updateSql, keyword.getName(),keyword.getSummary(),keyword.getId());
				return updateStatus;
				
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
		return 0;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getChildCount/{id}/{itemId}")
	public long getChildCount(@PathParam("id") int id,@PathParam("itemId") int itemId)
	{
		try
		{
			String sql = "select code from model_item where id=?";
			String code = runner.query(conn, sql, new ScalarHandler<String>(),itemId);
			if(code != null)
			{
				sql = "select count(id) from " + code + " where parent_id=?";
				long count = runner.query(conn, sql, new ScalarHandler<Long>(),id);
				return count;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
		return 0;
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("deleteById/{id}/{itemId}")
	public int deleteById(@PathParam("id") int id,@PathParam("itemId") int itemId)
	{
		try
		{
			String sql = "select code from model_item where id=?";
			String code = runner.query(conn, sql, new ScalarHandler<String>(),itemId);
			if(code != null)
			{
				sql = "delete from " + code + " where id=?";
				int state = runner.update(conn, sql, id);
				return state;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
		return 0;
	}
}
