package com.yoyo.service;

import com.yoyo.entity.Admins;
import java.util.List;

public interface IAdminService {


    /**
     * 用户名是否存在
     *
     * @param username
     * @return
     */
    boolean isExist(String username);

    /**
     * 通过用户名获取
     *
     * @param username
     * @return
     */
    Admins getByUsername(String username);

    /**
     * 列表
     *
     * @param page
     * @param rows
     * @return
     */
    List<Admins> getList(int page, int rows);

    /**
     * 总数
     *
     * @return
     */
    long getTotal();

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Admins get(int id);

    /**
     * 添加
     *
     * @param admin
     */
    Integer add(Admins admin);

    /**
     * 更新
     *
     * @param admin
     */
    boolean update(Admins admin);

    /**
     * 删除
     *
     * @param
     */
    boolean delete(Admins admin);
}
