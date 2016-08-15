package com.restful;

public class Organization
{
	private int id;
	private String name;
	private String summary;
	private int categoryId;
	private int parentId;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSummary()
	{
		return summary;
	}
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	public int getCategoryId()
	{
		return categoryId;
	}
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
	public int getParentId()
	{
		return parentId;
	}
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}
	
}
