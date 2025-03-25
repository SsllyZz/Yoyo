package com.yoyo.service;

import com.yoyo.entity.Tops;

import java.util.List;

public interface ITopService {
    /**
     * 获取列表
     *
     * @return
     */
    List<Tops> getList(byte type, int page, int size);

    List<Tops> getSelectList(List<Integer>neighbors,byte type, int page, int size);
    /**
     * 获取总数
     *
     * @param type
     * @return
     */
    long getTotal(byte type);

    /**
     * 获取列表
     *
     * @return
     */
    List<Tops> getListByGoodid(int goodid);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Tops get(int id);

    /**
     * 添加
     *
     * @param top
     * @return
     */
    Integer add(Tops top);

    /**
     * 更新
     *
     * @param top
     */
    boolean update(Tops top);

    /**
     * 删除
     *
     * @param top
     */
    boolean delete(Tops top);

    /**
     * 按商品删除
     *
     * @param goodid
     * @return
     */
    boolean deleteByGoodid(int goodid);
}
