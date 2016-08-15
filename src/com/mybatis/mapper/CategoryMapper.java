package com.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.pojo.Category;

public interface CategoryMapper
{
	@Results({
		@Result(property = "id",column = "id"),
		@Result(property = "name",column = "name")
	})
	
	@Insert("INSERT INTO category (name) values (#{name})")
	@Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
	int insertCategory(Category category);
	
	@Select("select id,name from category")
	List<Category> getCategories();
	
	@Delete("delete from category where id=#{id}")
	void deleteCategory(int id);
}
