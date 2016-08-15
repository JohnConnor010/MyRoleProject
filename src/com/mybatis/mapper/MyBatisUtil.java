package com.mybatis.mapper;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil
{
	private static SqlSessionFactory factory;
	private static Reader reader;
	static
	{
		try
		{
			reader = Resources.getResourceAsReader("mybatis-config.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static SqlSessionFactory getSessionFactory()
	{
		return factory;
	}
}
