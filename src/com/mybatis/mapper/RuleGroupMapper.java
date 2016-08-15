package com.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.RuleGroup;

public interface RuleGroupMapper
{
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "name",column = "name")
	})
	@Insert("INSERT rule_group (name) VALUES (#{name})")
	int insert(RuleGroup ruleGroup);
    
	@Select("SELECT id,name from rule_group")
	List<RuleGroup> select();
    
	@Update("UPDATE rule_group set name=#{name} where id=#{id}")
	int update(RuleGroup ruleGroup);
    
	@Delete("DELETE FROM rule_group where id=#{id}")
	int delete(int id);
    
    @Select("SELECT id,name FROM rule_group")
    List<Map<String,Object>> findAll();
}
