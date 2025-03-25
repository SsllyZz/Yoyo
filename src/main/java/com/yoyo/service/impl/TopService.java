package com.yoyo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.yoyo.dao.TopsDao;
import com.yoyo.entity.Goods;
import com.yoyo.entity.Tops;
import com.yoyo.entity.Users;
import com.yoyo.service.IGoodService;
import com.yoyo.service.ITopService;
import com.yoyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 商品推荐服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class TopService implements ITopService{

	@Autowired	
	private TopsDao topDao;
	@Autowired
	private IGoodService iGoodService;
	@Autowired
	private IUserService iUserService;
	
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Tops> getList(byte type, int page, int size){
		List<Tops> topList = topDao.getList(type, (page-1)*size, size);
		for(Tops top : topList) {
			top.setGood(iGoodService.get(top.getGoodId()));
		}
		return topList;
	}


	public List<Tops> getSelectList(List<Integer> neighbors, byte type, int page, int size) {
		List<Tops> topList = topDao.getAllTops();  // 获取所有 Tops 数据
		List<Tops> filteredList = new ArrayList<>();

		// 筛选符合条件的 Tops
		for (Tops top : topList) {
			if (Objects.equals(top.getType(), type)) {
				Goods good = iGoodService.get(top.getGoodId());
				if (neighbors.contains(good.getOwnerId())) {
					top.setGood(good);
					filteredList.add(top);
				}
			}
		}

		// 计算分页的起始位置和结束位置
		int start = (page - 1) * size;  // 起始索引
		int end = Math.min(start + size, filteredList.size());  // 结束索引，避免越界

		// 返回分页后的结果
		return filteredList.subList(start, end);  // 获取分页数据
	}

	/**
	 * 获取总数
	 * @param type
	 * @return
	 */
	public long getTotal(byte type) {
		return topDao.getTotal(type);
	}
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Tops> getListByGoodid(int goodid){
		return topDao.getListByGoodid(goodid);
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Tops get(int id) {
		return topDao.selectById(id);
	}
	
	/**
	 * 添加
	 * @param top
	 * @return
	 */
	public Integer add(Tops top) {
		return topDao.insert(top);
	}

	/**
	 * 更新
	 * @param top
	 */
	public boolean update(Tops top) {
		return topDao.updateById(top) > 0;
	}

	/**
	 * 删除
	 * @param top
	 */
	public boolean delete(Tops top) {
		return (Objects.nonNull(top.getId())) ? (topDao.deleteById(top.getId()) > 0) :
			topDao.deleteByGoodidAndType(top.getGoodId(), top.getType());
	}
	
	/**
	 * 按商品删除
	 * @param goodid
	 * @return
	 */
	public boolean deleteByGoodid(int goodid) {
		return topDao.deleteByGoodid(goodid);
	}
	
}
