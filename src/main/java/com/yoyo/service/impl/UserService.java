package com.yoyo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.yoyo.dao.UsersDao;
import com.yoyo.entity.Users;
import com.yoyo.service.IUserService;
import com.yoyo.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class UserService implements IUserService{

	@Autowired		//spring注入类对象
	private UsersDao userDao;
	
	/**
	 * 验证用户密码
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(String username, String password){
		return userDao.getByUsernameAndPassword(username, SafeUtil.encode(password)) != null;
	}

	/**
	 * 用户是否存在
	 * @param username
	 * @return
	 */
	public boolean isExist(String username) {
		return userDao.getByUsername(username) != null;
	}

	/**
	 * 重置密码
	 * @param users
	 * @return
	 */
	public Users reset(Users users){
		return userDao.reset(users);
	};

	/**
	 * 添加
	 * @param user
	 * @return
	 */
	public boolean add(Users user) {
		//密码加密
		user.setPassword(SafeUtil.encode(user.getPassword()));
		return userDao.insert(user) > 0;
	}
	
	/**
	 * 通过id获取
	 * @param userid
	 * @return
	 */
	public Users get(int userid){
		return userDao.selectById(userid);
	}
	
	/**
	 * 通过username获取
	 * @param username
	 * @return
	 */
	public Users get(String username){
		return userDao.getByUsername(username);
	}
	
	/**
	 * 列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Users> getList(int page, int rows) {
		return userDao.getList(rows * (page-1), rows);
	}

	/**
	 * 总数
	 * @return
	 */
	public long getTotal() {
		return userDao.getTotal();
	}

	/**
	 * 更新
	 * @param user
	 */
	public boolean update(Users user) {
		return userDao.updateById(user) > 0;
	}


	public boolean updateId(Users user) {
		user.setPassword(SafeUtil.encode(user.getPassword()));
		return userDao.updateByIdSelective(user) > 0;
	}

	/**
	 * 删除
	 * @param
	 */
	public boolean delete(Users user) {
		return userDao.deleteById(user.getId()) > 0;
	}

//	找邻居
	public List<Integer> neighbors(Users user) {
		List<Users>list=userDao.getAllList();
		List<Integer>list2=new ArrayList<>();
		for(Users user2 : list) {
			if (user2.getAddress().equals(user.getAddress())) {
				list2.add(user2.getId());
			}
		}
		return list2;
	}
}
