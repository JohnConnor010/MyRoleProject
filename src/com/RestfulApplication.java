package com;

import javax.ws.rs.ApplicationPath;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import com.restful.CascadingService;
import com.restful.CheckDatabase;
import com.restful.DeleteItemService;
import com.restful.GetNodeService;
import com.restful.KeywordService;
import com.restful.OrganizationService;
import com.restful.RuleService;

@ApplicationPath("api")
public class RestfulApplication extends ResourceConfig
{
	public RestfulApplication()
	{
		packages(CheckDatabase.class.getPackage().getName());
		packages(DeleteItemService.class.getPackage().getName());
		packages(GetNodeService.class.getPackage().getName());
		packages(CascadingService.class.getPackage().getName());
		packages(KeywordService.class.getPackage().getName());
		packages(OrganizationService.class.getPackage().getName());
		packages(RuleService.class.getPackage().getName());
		register(JacksonJsonProvider.class);
	}
}
