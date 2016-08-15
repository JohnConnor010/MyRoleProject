package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.thymeleaf.context.WebContext;

import com.DatabaseConfig;
import com.ThymeleafUtils;

@WebServlet("/add_item")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static QueryRunner runner;
	private static Connection conn;
	
	static
	{
		DbUtils.loadDriver(DatabaseConfig.getDriver());
		runner = new QueryRunner();
		conn= DatabaseConfig.getConnection();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
		String action = request.getParameter("action");
		String id = null;
		request.setAttribute("action", action);
		if(action != null)
		{
			if(action.equals("edit"))
			{
				id = request.getParameter("id");
				request.setAttribute("id", id);
				try
				{
					Map<String, Object> singleData = runner.query(conn, "select * from model_item where id=?", new MapHandler(),id);
					request.setAttribute("item_name", singleData.get("name"));
					request.setAttribute("item_code", singleData.get("code"));
					request.setAttribute("item_summary", singleData.get("summary"));
					request.setAttribute("readonly", true);
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
			
		}
		try
		{
			List<Map<String, Object>> results = runner.query(conn, "select * from model_item where id <> 11 and id <> 32 order by id desc", new MapListHandler());
			request.setAttribute("results", results);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			/*DbUtils.closeQuietly(conn);*/
		}
		ThymeleafUtils.getTemplateEngine().process("item", ctx,response.getWriter());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String summary = request.getParameter("summary");		
		String hideAction = request.getParameter("hide_action");
		if(hideAction.equals(""))
		{
			String checkSql = "select count(id) from model_item where code=? and name=?";			
			try
			{
				Long count = runner.query(conn, checkSql, new ScalarHandler<Long>(),code,name);
				if(count == 0)
				{
					String createTableSql = "CREATE TABLE `data`.`" + code + "` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(200) NULL,`parent_id` INT NULL DEFAULT 0,`summary` TEXT NULL,PRIMARY KEY (`id`));";
					int update = runner.update(conn, createTableSql);
					String insertSql = "INSERT INTO `data`.`model_item`(`name`,`code`,`summary`) VALUES (?,?,?);";				
					int inserts = runner.update(conn, insertSql, name,code,summary);					
				}
				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(hideAction.equals("edit"))
		{
			String hideId = request.getParameter("hide_id");
			String updateSql = "update model_item set name=?,summary=? where id=?";
			try
			{
				int state = runner.update(conn, updateSql, name,summary,hideId);
				System.out.println(state);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}

}
