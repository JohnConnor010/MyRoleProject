package com;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.util.TemplateModeUtils;

public class ThymeleafUtils
{
	private static TemplateEngine engine;
	
	static
	{
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setTemplateMode("HTML5");
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setSuffix(".html");
		/*resolver.setCacheTTLMs(3600000L);*/
		resolver.setCacheable(false);
		resolver.setCharacterEncoding("UTF-8");
		engine = new TemplateEngine();
		engine.setTemplateResolver(resolver);
		
	}
	public static TemplateEngine getTemplateEngine()
	{
		return engine;
	}
}
