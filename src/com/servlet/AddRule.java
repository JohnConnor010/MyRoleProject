package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mybatis.mapper.ModelItemMapper;
import com.mybatis.mapper.RuleGroupMapper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.WebContext;

import com.DatabaseConfig;
import com.ThymeleafUtils;
import com.mybatis.mapper.MyBatisUtil;
import com.mybatis.mapper.RulesMapper;
import com.pojo.Rules;


@WebServlet("/add_rule")
public class AddRule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private static QueryRunner runner;
	static
	{
		DbUtils.loadDriver(DatabaseConfig.getDriver());
		conn = DatabaseConfig.getConnection();
		runner = new QueryRunner();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        SqlSession session = MyBatisUtil.getSessionFactory().openSession();
        try
        {
            RuleGroupMapper ruleGroupMapper = session.getMapper(RuleGroupMapper.class);
            List<Map<String,Object>> groups = ruleGroupMapper.findAll();
            Map<String,Object> groupMap = new HashMap<>();
            groupMap.put("id", 0);
            groupMap.put("name", "选择组名");
            groups.add(0, groupMap);
            ctx.setVariable("groups", groups);
    
            ModelItemMapper modelItemMapper = session.getMapper(ModelItemMapper.class);
            List<Map<String,Object>> maps = modelItemMapper.findAllModelItems();
            HashMap<String, Object> indexMap = new HashMap<>();
            indexMap.put("code","");
            indexMap.put("name", "选择项");
            maps.add(0, indexMap);
            HashMap<String, Object> zihaoMap = new HashMap<>();
            zihaoMap.put("code", "zihao");
            zihaoMap.put("name", "字号");
            maps.add(zihaoMap);
            ctx.setVariable("options", maps);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session.close();
        }
		ThymeleafUtils.getTemplateEngine().process("add_rule", ctx,response.getWriter());
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();	
		String ddlGroup = request.getParameter("ddlGroup");
		String rule = request.getParameter("rule");
		String hideRule = request.getParameter("hide_rule");
		String hide_shuzu = request.getParameter("hide_shuzu");
		String hide_yinchangyu = request.getParameter("hide_yinchangyu");
		String hide_yinchangstr = request.getParameter("hide_yinchangstr");
		String hideAction = request.getParameter("hide_action");		
		SqlSession session = MyBatisUtil.getSessionFactory().openSession();
		if(hideAction.equals("save"))
		{
			try
			{
				RulesMapper mapper = session.getMapper(RulesMapper.class);
				Rules rules = new Rules();
				rules.setRule_expression(rule);
				rules.setRule_value(hideRule);
				rules.setGroup_id(Integer.valueOf(ddlGroup));
				rules.setShuzu(hide_shuzu);
				rules.setYinchangyu(hide_yinchangyu);
				rules.setYinchangstr(hide_yinchangstr);
				mapper.insertRules(rules);
				session.commit();
				out.println("数据保存成功");
			}
			catch(Exception ex)
			{
				out.println("数据保存失败");
			}
			finally
			{
				session.close();
			}
		}
		if(hideAction.equals("edit"))
		{
			String editId = request.getParameter("hide_editId");
			try
			{
				RulesMapper mapper = session.getMapper(RulesMapper.class);
				Rules rules = new Rules();
				rules.setId(Integer.valueOf(editId));
				rules.setRule_expression(rule);
				rules.setRule_value(hideRule);
				rules.setGroup_id(Integer.valueOf(ddlGroup));
				rules.setShuzu(hide_shuzu);
				rules.setYinchangyu(hide_yinchangyu);
				rules.setYinchangstr(hide_yinchangstr);
				mapper.updateRules(rules);
				session.commit();
				out.println("数据更新成功");
			}
			catch(Exception ex)
			{
				out.println("数据更新失败");
			}
			finally
			{
				session.close();
			}
			System.out.println(editId);
			System.out.println("执行编辑");
		}
		
	}

}
