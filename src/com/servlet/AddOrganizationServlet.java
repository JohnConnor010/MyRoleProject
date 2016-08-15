package com.servlet;

import java.io.IOException;
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

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.thymeleaf.context.WebContext;

import com.DatabaseConfig;
import com.ThymeleafUtils;

/**
 * Servlet implementation class AddOrganizationServlet
 */
@WebServlet("/add_organization")
public class AddOrganizationServlet extends HttpServlet {
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
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		WebContext ctx = new WebContext(request, response, request.getServletContext(),request.getLocale());
		
		String select = "select * from category";
		try
		{
			List<Map<String, Object>> options = runner.query(conn, select, new MapListHandler());
			Map<String, Object> option = new HashMap<>();
			option.put("id", 0);
			option.put("name", "选择分类");
			options.add(0, option);
			ctx.setVariable("options", options);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ThymeleafUtils.getTemplateEngine().process("add_organization", ctx,response.getWriter());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
