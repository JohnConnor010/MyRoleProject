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
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.thymeleaf.context.WebContext;

import com.DatabaseConfig;
import com.ThymeleafUtils;

/**
 * Servlet implementation class AddKeywordServlet
 */
@WebServlet("/add_keyword")
public class AddKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection conn;
	private static QueryRunner runner;
	static
	{
		DbUtils.loadDriver(DatabaseConfig.getDriver());
		conn = DatabaseConfig.getConnection();
		runner = new QueryRunner();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
		String id = request.getParameter("id");
		request.setAttribute("itemId", id);
		if(id != null)
		{
			
			try
			{
				String query = "select code from model_item where id=?";
				String code = runner.query(conn, query, new ScalarHandler<String>(),id);
				if(code != null)
				{
					String sql = "SELECT id,name FROM " + code + " where parent_id=0;";
					List<Map<String, Object>> maps = runner.query(conn, sql, new MapListHandler());
					Map<String, Object> map = new HashMap<>();
					map.put("id", 0);
					map.put("name", "作为父节点");
					maps.add(0, map);
					request.setAttribute("options", maps);
				}
				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ThymeleafUtils.getTemplateEngine().process("add_keyword", ctx,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
