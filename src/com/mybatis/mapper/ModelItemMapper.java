package com.mybatis.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by JohnC on 2016-08-15.
 */
public interface ModelItemMapper
{
    @Select("SELECT code,name FROM model_item WHERE id <> 11 AND id <> 32")
    @Results({
            @Result(property = "code",column = "code"),
            @Result(property = "summary",column = "summary")
    })
    List<Map<String,Object>> findAllModelItems();
}
