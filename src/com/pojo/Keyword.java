package com.pojo;

import java.math.BigInteger;

public class Keyword
{
	private BigInteger id;
	private String name;
	private int parent_id;
	private String summary;
	private int itemId;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getParent_id()
	{
		return parent_id;
	}
	public void setParent_id(int parent_id)
	{
		this.parent_id = parent_id;
	}
	public String getSummary()
	{
		return summary;
	}
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	public BigInteger getId()
	{
		return id;
	}
	public void setId(BigInteger id)
	{
		this.id = id;
	}
	public int getItemId()
	{
		return itemId;
	}
	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}
	
	
}
