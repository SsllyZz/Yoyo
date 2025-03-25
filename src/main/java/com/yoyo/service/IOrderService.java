package com.yoyo.service;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Items;
import com.yoyo.entity.Orders;

import java.util.List;

public interface IOrderService {

    /**
     * 创建订单
     *
     * @param good
     * @return
     */
    Orders add(Goods good);

    /**
     * 向订单添加项目
     *
     * @param order
     * @param good
     * @return
     */
    Orders addOrderItem(Orders order, Goods good);

    /**
     * 从订单中减少项目
     *
     * @param order
     * @param good
     * @return
     */
    Orders lessenIndentItem(Orders order, Goods good);

    /**
     * 从订单中删除项目
     *
     * @param order
     * @param good
     * @return
     */
    Orders deleteIndentItem(Orders order, Goods good);

    /**
     * 保存订单
     *
     * @param order
     */
    int save(Orders order);

    /**
     * 订单支付
     *
     * @param order
     */
    void pay(Orders order);

    /**
     * 获取订单列表
     *
     * @param page
     * @param row
     * @return
     */
    List<Orders> getList(byte status, int page, int row);

    /**
     * 获取总数
     *
     * @return
     */
    int getTotal(byte status);

    /**
     * 订单发货
     *
     * @param id
     * @return
     */
    boolean dispose(int id);

    /**
     * 订单完成
     *
     * @param id
     * @return
     */
    boolean finish(int id);

    /**
     * 删除订单
     *
     * @param id
     */
    boolean delete(int id);

    /**
     * 获取某用户全部订单
     *
     * @param userid
     */
    List<Orders> getListByUserid(int userid);
    List<Orders> getListBySellerid(int userid);
    /**
     * 通过id获取
     *
     * @param orderid
     * @return
     */
    Orders get(int orderid);


    /**
     * 获取订单项目列表
     *
     * @param orderid
     * @return
     */
    List<Items> getItemList(int orderid);

}
