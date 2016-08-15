package com.restful;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.ibatis.session.SqlSession;

import com.DatabaseConfig;
import com.mybatis.mapper.MyBatisUtil;
import com.mybatis.mapper.RuleGroupMapper;
import com.mybatis.mapper.RulesMapper;
import com.pojo.RuleGroup;
import com.pojo.Rules;

@Path("/group")
public class RuleGroupService
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
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/save")
	public int sageGroup(RuleGroup data)
	{		
		try
		{			
			String query = "select count(id) from rule_group where name=?";
			long queryState = runner.query(conn, query, new ScalarHandler<Long>(),data.getName());
			if(queryState == 0)
			{
				String insert = "insert into rule_group (name) value (?)";
				int insertState = runner.update(conn, insert, data.getName());
				return insertState;
			}
			else
			{
				return -1;
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get")
	public List<Map<String, Object>> get()
	{
		String query = "select * from rule_group";
		try
		{
			List<Map<String, Object>> result = runner.query(conn, query, new MapListHandler());
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save/cust")
	public Map<String, Object> saveCust(RuleGroup data)
	{
		
		try
		{
			Map<String, Object> map = new HashMap<String,Object>();
			String query = "select count(id) from cust where name=?";
			long queryState = runner.query(conn, query, data.getName(), new ScalarHandler<Long>());
			if(queryState > 0)
			{
				map.put("id", 0);
				map.put("name", "");
				map.put("text", "");
				map.put("count", queryState);
			}
			else
			{
				String insert = "insert into cust (name) values (?)";
				int insertState = runner.update(conn, insert, data.getName());
				if(insertState > 0)
				{
					String select_last_id = "SELECT LAST_INSERT_ID();";
					BigInteger lastInsertId = runner.query(conn, select_last_id, new ScalarHandler<BigInteger>());
					
					map.put("id", lastInsertId);
					map.put("name","[cust-"+ lastInsertId +"]");
					map.put("text", data.getName());
					map.put("count", 0);
				}
				
			}			
			return map;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/{groupId}")
	public List<Map<String, Object>> getByGroupId(@PathParam("groupId") int groupId)
	{
		String query = "select * from rules where group_id=?";
		try
		{
			List<Map<String, Object>> rules = runner.query(conn, query, new MapListHandler(),groupId);
			String groupName = runner.query(conn, "select name from rule_group where id=?", new ScalarHandler<String>(),groupId);
			if(groupName == null)
			{
				groupName = "未知";
			}
			for (Map<String, Object> map : rules)
			{
				map.put("groupName", groupName);
			}
			return rules;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/delete/{id}")
	public int deleteById(@PathParam("id") int id)
	{
		String delete = "delete from rules where id=?";
		try
		{
			int deleteStatus = runner.update(conn, delete, id);
			return deleteStatus;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	@POST
	@Path("/rulegroup/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public int updateRuleGroup(RuleGroup ruleGroup)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RuleGroupMapper mapper = session.getMapper(RuleGroupMapper.class);
			result = mapper.update(ruleGroup);
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("/rulegroup/delete/{id}")
	public int deleteRuleGroupById(@PathParam("id") int id)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RuleGroupMapper mapper = session.getMapper(RuleGroupMapper.class);
			result = mapper.delete(id);
			if(result > 0)
			{
				RulesMapper rulesMapper = session.getMapper(RulesMapper.class);
				rulesMapper.deleteRulesByGroupId(id);
			}
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
}
