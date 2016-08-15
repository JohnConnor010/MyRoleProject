package com.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.Rules;

public interface RulesMapper
{
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "group_id",column = "group_id"),
		@Result(property = "rule_value",column = "rule_value"),
		@Result(property = "rule_expression",column = "rule_expression"),
		@Result(property = "shuzu",column = "shuzu"),
		@Result(property = "yinchangyu",column = "yinchangyu"),
		@Result(property = "yinchangstr",column = "yinchangstr")
	})
	@Insert("INSERT INTO `data`.`rules` (`group_id`, `rule_expression`, `rule_value`, `shuzu`, `yinchangyu`, `yinchangstr`) VALUES (#{group_id}, #{rule_expression}, #{rule_value}, #{shuzu}, #{yinchangyu}, #{yinchangstr});")
	int insertRules(Rules rules);
	@Select("SELECT id,group_id,rule_value,rule_expression,shuzu,yinchangyu,yinchangstr FROM `data`.`rules` WHERE group_id=#{groupId}")
	List<Rules> getRules(int groupId);
	@Delete("DELETE FROM rules WHERE id=#{id}")
	int deleteRules(int id);
	@Select("SELECT * FROM rules where id=#{id}")
	Rules getRulesById(int id);
	@Update("UPDATE rules set rule_expression=#{rule_expression},rule_value=#{rule_value},shuzu=#{shuzu},yinchangyu=#{yinchangyu},yinchangstr=#{yinchangstr} where id=#{id}")
	int updateRules(Rules rules);
	@Delete("DELETE FROM rules WHERE group_id=#{group_id}")
	int deleteRulesByGroupId(int group_id);
}
