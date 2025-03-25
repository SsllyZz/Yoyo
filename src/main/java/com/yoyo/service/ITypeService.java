package com.yoyo.service;

import com.yoyo.entity.Types;

import java.util.List;

public interface ITypeService {

    /**
     * 获取列表
     *
     * @return
     */
    List<Types> getList();

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Types get(int id);

    /**
     * 添加
     *
     * @param type
     * @return
     */
    Integer add(Types type);

    /**
     * 更新
     *
     * @param type
     */
    boolean update(Types type);

    /**
     * 删除
     *
     * @param type
     */
    boolean delete(Types type);
}
