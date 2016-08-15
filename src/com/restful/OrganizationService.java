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
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.ibatis.session.SqlSession;

import com.DatabaseConfig;
import com.mybatis.mapper.CategoryMapper;
import com.mybatis.mapper.MyBatisUtil;
import com.pojo.Category;
import com.pojo.EasyUITree;

@Path("/organization")
public class OrganizationService
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
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EasyUITree save(Organization data)
	{
		EasyUITree treeData = new EasyUITree();
		String insert = "insert into organization (name,parent_id,summary,category_id) values (?,?,?,?)";
		try
		{
			int insertState = runner.update(conn, insert, data.getName(),data.getParentId(),data.getSummary(),data.getCategoryId());
			if(insertState > 0)
			{
				String select = "select last_insert_id();";
				BigInteger lastIndentityId = runner.query(conn, select, new ScalarHandler<BigInteger>());
				treeData.setId(lastIndentityId);
				treeData.setText(data.getName());
				return treeData;
			}
		}
		catch (SQLException e)
		{
			return null;
		}
		
		return treeData;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get_data/{id}")
	public Map<String, Object> getDataById(@PathParam("id") int id)
	{
		Map<String, Object> result = new HashMap<>();
		String query = "select * from organization where id=?";
		try
		{
			Map<String, Object> map = runner.query(conn, query, new MapHandler(),id);
			String select = "select name from organization where id=?";
			String name = runner.query(conn, select, new ScalarHandler<String>(),map.get("parent_id"));
			result.put("map", map);
			result.put("parent_text", name);
			return result;
		}
		catch (SQLException e)
		{
			return null;
		}
	}
	
	@POST
	@Path("update")
	@Produces(MediaType.TEXT_PLAIN)
	public int update(Organization data)
	{
		Map<String, Object> map = new HashMap<>();
		String update = "update organization set name=?,parent_id=?,summary=?,category_id=? where id=?";
		try
		{
			int updateState = runner.update(conn, update, data.getName(),data.getParentId(),data.getSummary(),data.getCategoryId(),data.getId());
			return updateState;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}
	@GET
	@Path("/delete/{id}")
	public int delete(@PathParam("id") int id)
	{
		String delete = "delete from organization where id=?";
		try
		{
			int deleteState = runner.update(conn, delete, id);
			return deleteState;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/savecategory")
	public int saveCategory(Category category)
	{
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);
			int insertStatus = mapper.insertCategory(category);
			session.commit();
			return insertStatus;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return 0;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCategories")
	public List<Category> getCategories()
	{
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{			
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);
			List<Category> result = mapper.getCategories();
			session.close();
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return null;
	}
	
	@GET
	@Path("/deleteCategory/{id}")
	public int deleteCategory(@PathParam("id") int id)
	{
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			CategoryMapper mapper = session.getMapper(CategoryMapper.class);
			mapper.deleteCategory(id);
			session.commit();
			return 1;
		}
		finally
		{
			session.close();
		}
	}
}
