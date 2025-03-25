package com.yoyo.dao;

import java.util.List;

import com.yoyo.entity.Types;
import org.apache.ibatis.annotations.Select;


public interface TypesDao {
    int deleteById(Integer id);

    int insert(Types record);

    int insertSelective(Types record);

    Types selectById(Integer id);

    int updateByIdSelective(Types record);

    int updateById(Types record);    
    
    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中
    
    // ------------------------------------------------------------
    
    // 以下方法使用mybatis注解实现
    
	/**
	 * 获取列表
	 * @return
	 */
    @Select("select * from types order by id desc")
	public List<Types> getList();
    
}