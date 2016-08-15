package com.pojo;

import java.math.BigInteger;

public class EasyUITree
{
	private BigInteger id;
	private String text;
	private String iconCls;
	private boolean checked;
	private String state;
	public BigInteger getId()
	{
		return id;
	}
	public void setId(BigInteger id)
	{
		this.id = id;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public String getIconCls()
	{
		return iconCls;
	}
	public void setIconCls(String iconCls)
	{
		this.iconCls = iconCls;
	}
	public boolean isChecked()
	{
		return checked;
	}
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	
}
