package com.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.Extension;


public interface ExtensionMapper
{
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "name",column = "name"),
		@Result(property = "parent_id",column = "parent_id"),
		@Result(property = "summary",column = "summary"),
		@Result(property = "group_id",column = "group_id")
	})
	@Insert("insert into extension (name,parent_id,summary,group_id) values (#{name},#{parent_id},#{summary},#{group_id})")
	void insertExtension(Extension extension);
	@Select("select * from extension where group_id=#{groupId}")
	List<Map<String, Object>> getExtensionByGroupId(int groupId);
	@Delete("delete from extension where id=#{id}")
	void deleteById(int id);
	@Select("select * from extension where id=#{id}")
	Extension getExtensionById(int id);
	@Update("UPDATE extension SET name=#{name},parent_id=#{parent_id},summary=#{summary}, group_id=#{group_id} WHERE id=#{id}")
	int update(Extension data);
}
