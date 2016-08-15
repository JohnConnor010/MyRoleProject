package com.restful;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;

import com.mybatis.mapper.CustomizeMapper;
import com.mybatis.mapper.ExtensionMapper;
import com.mybatis.mapper.MyBatisUtil;
import com.mybatis.mapper.RuleGroupMapper;
import com.mybatis.mapper.RulesMapper;
import com.pojo.Customize;
import com.pojo.Extension;
import com.pojo.RuleGroup;
import com.pojo.Rules;

@Path("rule")
public class RuleService
{
	@POST
	@Path("save/extension")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public int saveExtension(Extension data)
	{
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			Extension _extension = new Extension();
			_extension.setName(data.getName());
			_extension.setGroup_id(data.getGroup_id());
			_extension.setSummary("");
			_extension.setParent_id(0);
			ExtensionMapper mapper = session.getMapper(ExtensionMapper.class);
			mapper.insertExtension(_extension);
			session.commit();
			return 1;
		}
		catch(Exception ex)
		{
			return 0;
		}
		finally
		{
			session.close();			
		}
	}
	@GET
	@Path("/get/extension/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getExtensionByGroupId(@PathParam("id") int groupId)
	{
		List<Map<String, Object>> result = null;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			ExtensionMapper mapper = session.getMapper(ExtensionMapper.class);
			result = mapper.getExtensionByGroupId(groupId);
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("/delete/extension/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public int deleteExtensionById(@PathParam("id") int id)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			ExtensionMapper mapper = session.getMapper(ExtensionMapper.class);
			mapper.deleteById(id);
			session.commit();
			result = 1;
		}
		catch(Exception exception)
		{
			result = 0;
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("get/extension/byid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Extension getExtensionById(@PathParam("id") int id)
	{
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			ExtensionMapper mapper = session.getMapper(ExtensionMapper.class);
			Extension extension = mapper.getExtensionById(id);
			session.commit();
			return extension;
		}
		catch(Exception ex)
		{
			return null;
		}
		finally
		{
			session.close();
		}
	}
	@POST
	@Path("/update/extension")
	@Consumes(MediaType.APPLICATION_JSON)
	public int updateExtension(Extension data)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			ExtensionMapper mapper = session.getMapper(ExtensionMapper.class);
			Extension _extension = new Extension();
			_extension.setName(data.getName());
			_extension.setParent_id(0);
			_extension.setSummary(null);
			_extension.setGroup_id(data.getGroup_id());
			_extension.setId(data.getId());
			int state = mapper.update(_extension);
			session.commit();
			return state;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			result = 0;
		}
		return result;
	}
	@GET
	@Path("/cust/getall/{groupId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customize> getAllCustomize(@PathParam("groupId") int groupId)
	{
		List<Customize> result = new ArrayList<>();
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			CustomizeMapper mapper = session.getMapper(CustomizeMapper.class);
			result = mapper.selectByGroupId(groupId);
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@POST
	@Path("cust/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public int saveCustomize(Customize customize)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			CustomizeMapper mapper = session.getMapper(CustomizeMapper.class);
			result = mapper.insert(customize);
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@POST
	@Path("cust/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public int updateCustomize(Customize customize)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			CustomizeMapper mapper = session.getMapper(CustomizeMapper.class);
			result = mapper.update(customize);
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("cust/delete/{id}")
	public int deleteCustomize(@PathParam("id") int id)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			CustomizeMapper mapper = session.getMapper(CustomizeMapper.class);
			result = mapper.delete(id);
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@POST
	@Path("group/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public int saveGroup(RuleGroup data)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RuleGroupMapper mapper = session.getMapper(RuleGroupMapper.class);
			result = mapper.insert(data);
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("group/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RuleGroup> getGroup()
	{
		List<RuleGroup> result = new ArrayList<>();
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RuleGroupMapper mapper = session.getMapper(RuleGroupMapper.class);
			result = mapper.select();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("getRules/{groupId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rules> getRulesByGroupId(@PathParam("groupId") int groupId)
	{
		List<Rules> result = new ArrayList<>();
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RulesMapper mapper = session.getMapper(RulesMapper.class);
			
			result = mapper.getRules(groupId);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("deleteRules/{id}")
	public int deleteRules(@PathParam("id") int id)
	{
		int result = 0;
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RulesMapper mapper = session.getMapper(RulesMapper.class);
			result = mapper.deleteRules(id);
			session.commit();
		}
		finally
		{
			session.close();
		}
		return result;
	}
	@GET
	@Path("getById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Rules getRuleById(@PathParam("id") int id)
	{
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		try
		{
			RulesMapper mapper = session.getMapper(RulesMapper.class);
			Rules rules = mapper.getRulesById(id);
			return rules;
		}
		catch (Exception e)
		{
			session.close();
		}
		return null;
	}
	
}
