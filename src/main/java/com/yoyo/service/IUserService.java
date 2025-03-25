package com.yoyo.service;

import com.yoyo.entity.Users;

import java.util.List;

public interface IUserService {

    /**
     * 验证用户密码
     *
     * @param username
     * @param password
     * @return
     */
    boolean checkUser(String username, String password);

    /**
     * 用户是否存在
     *
     * @param username
     * @return
     */
    boolean isExist(String username);

    /**
     * 添加
     *
     * @param user
     * @return
     */
    boolean add(Users user);

    /**
     *
     * @param users
     * @return
     */
    Users reset(Users users);



    /**
     * 通过id获取
     *
     * @param userid
     * @return
     */
    Users get(int userid);

    /**
     * 通过username获取
     *
     * @param username
     * @return
     */
    Users get(String username);

    /**
     * 列表
     *
     * @param page
     * @param rows
     * @return
     */
    List<Users> getList(int page, int rows);

    /**
     * 总数
     *
     * @return
     */
    long getTotal();

    /**
     * 更新
     *
     * @param user
     */
    boolean update(Users user);


    boolean updateId(Users user);

    /**
     * 删除
     *
     * @param user
     */
    boolean delete(Users user);

    public List<Integer> neighbors(Users user);

}
