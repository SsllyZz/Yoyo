package com.yoyo.service;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Users;

import java.util.List;

public interface IGoodService {

    /**
     * 获取列表
     *
     * @param page
     * @param size
     * @return
     */
    List<Goods> getList(int status, int page, int size);

    /**
     * 获取产品总数
     *
     * @return
     */
    long getTotal(int status);

    /**
     * 通过名称获取产品列表
     *
     * @param page
     * @param size
     * @return
     */
    List<Goods> getListByName(String name, int page, int size);

    /**
     * 通过名称获取产品总数
     *
     * @return
     */
    long getTotalByName(String name);

    /**
     * 通过分类搜索
     *
     * @param typeId
     * @param page
     * @param size
     * @return
     */
    List<Goods> getListByType(int typeId, int page, int size);
    List<Goods> getSelectedList(List<Integer>neighbors, int page, int size);
    long getTotalSelected( List<Integer> neighbors);
    List<Goods> getSelectedListByType(List<Integer>neighbors,int typeId, int page, int size);
    public long getTotalSelectedByType(int typeId, List<Integer> neighbors);
    /**
     * 获取数量
     *
     * @param typeId
     * @return
     */
    long getTotalByType(int typeId);

    /**
     * 通过id获取
     *
     * @param productId
     * @return
     */
    Goods get(int productId);

    /**
     * 添加
     *
     * @param good
     */
    Integer add(Goods good);

    /**
     * 修改
     *
     * @param good
     * @return
     */
    boolean update(Goods good);

    /**
     * 删除商品
     * 先删除此商品的推荐信息
     *
     * @param goodid
     */
    boolean delete(int goodid);


    List<Goods> getMyList(Users user);
}
