package com.yoyo.dao;

import com.yoyo.entity.Admins;
import com.yoyo.entity.Communities;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommunitiesDAO {
    int deleteById(Integer id);

    int insert(Communities communities);

    int insertSelective(Communities communities);

    Communities selectById(Integer id);

    int updateByIdSelective(Communities communities);

    int updateById(Communities communities);

    // 以上为mybatis generator自动生成接口, 具体实现在mapper.xml中

    // ------------------------------------------------------------

    // 以下方法使用mybatis注解实现

    /**
     * 获取列表
     * @return
     */
    @Select("select * from communities order by id desc")
    public List<Communities> getList();

    /**
     * 通过小区名查找
     * @param name
     * @return
     */
    @Select("select * from communities where name=#{name}")
    public Communities getByName(String name);

}
