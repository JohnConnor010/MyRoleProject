package com.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.Customize;

public interface CustomizeMapper
{
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "name",column = "name"),
		@Result(property = "groupId",column = "groupId")
	})
	@Insert("INSERT INTO cust (name,groupId) values (#{name},#{groupId})")
	int insert(Customize customize);
	@Delete("DELETE FROM cust where id=#{id}")
	int delete(int id);
	@Update("UPDATE cust set name=#{name},groupId=#{groupId} WHERE id=#{id}")
	int update(Customize customize);
	@Select("SELECT id,name,groupId from cust where groupId=#{groupId}")
	List<Customize> selectByGroupId(int groupId);
	@Select("SELECT id,name,groupId from cust where id=#{id}")
	Customize selectById(int id);
	
}
