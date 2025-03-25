package com.yoyo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.yoyo.dao.GoodsDao;
import com.yoyo.entity.Goods;
import com.yoyo.entity.Tops;
import com.yoyo.entity.Users;
import com.yoyo.service.IGoodService;
import com.yoyo.service.ITopService;
import com.yoyo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 商品服务
 */
@Service	// 注解为service层spring管理bean
@Transactional	// 注解此类所有方法加入spring事务, 具体设置默认
public class GoodService implements IGoodService {

	@Autowired	
	private GoodsDao goodDao;
	@Autowired
	private ITopService iTopService;
	@Autowired
	private ITypeService iTypeService;
	
	
	/**
	 * 获取列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getList(int status, int page, int size){
		if (status == 0) {
			return packTopList(goodDao.getList(size * (page-1), size));
		}
		List<Tops> topList = iTopService.getList((byte)status, page, size);
		if(topList!=null && !topList.isEmpty()) {
			List<Goods> goodList = new ArrayList<>();
			for(Tops top : topList) {
				goodList.add(packTop(goodDao.selectById(top.getGoodId())));
			}
			return goodList;
		}
		return null;
	}
	/**
	 * 获取列表
	 *
	 */
	public List<Goods> getMyList(Users user){
		List<Goods>goodsList=goodDao.getAllList();
		List<Goods> goods=new ArrayList<>();
		for(Goods good : goodsList) {
			if (Objects.equals(good.getOwnerId(), user.getId())) {
				goods.add(good);
			}
		}return goods;
	}
	/**
	 * 获取产品总数
	 * @return
	 */
	public long getTotal(int status){
		if (status == 0) {
			return goodDao.getTotal();
		}
		return iTopService.getTotal((byte)status);
	}
	
	/**
	 * 通过名称获取产品列表
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByName(String name, int page, int size){
		return goodDao.getListByName(name, size * (page-1), size);
	}
	
	/**
	 * 通过名称获取产品总数
	 * @return
	 */
	public long getTotalByName(String name){
		return goodDao.getTotalByName(name);
	}

	/**
	 * 通过分类搜索
	 * @param typeid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Goods> getListByType(int typeid, int page, int size) {
		return typeid > 0 ? goodDao.getListByType(typeid, size * (page-1), size) : goodDao.getList(size * (page-1), size);
	}


	public List<Goods> getSelectedList(List<Integer> neighbors, int page, int size) {
		// 获取所有商品
		List<Goods> goods = goodDao.getAllList();
		List<Goods> selectedGoods = new ArrayList<>();

		// 筛选符合条件的商品
		for(Goods good : goods) {
			if(neighbors.contains(good.getOwnerId()) ) {
				selectedGoods.add(good);
			}
		}
		// 计算分页的起始和结束索引
		int startIndex = (page - 1) * size;  // 从第几条开始
		int endIndex = Math.min(startIndex + size, selectedGoods.size()); // 防止越界

		// 截取指定页的数据
		return selectedGoods.subList(startIndex, endIndex);
	}

	public long getTotalSelected(List<Integer> neighbors) {
		// 获取所有商品
		List<Goods> goods = goodDao.getAllList();
		List<Goods> selectedGoods = new ArrayList<>();

		// 筛选符合条件的商品
		for(Goods good : goods) {
			if(neighbors.contains(good.getOwnerId()) ) {
				selectedGoods.add(good);
			}
		}
		return selectedGoods.size();
	}


	public List<Goods> getSelectedListByType(List<Integer> neighbors, int typeId, int page, int size) {
		// 获取所有商品
		List<Goods> goods = goodDao.getAllList();
		List<Goods> selectedGoods = new ArrayList<>();

		// 筛选符合条件的商品
		for(Goods good : goods) {
			if(neighbors.contains(good.getOwnerId()) && good.getTypeId() == typeId) {
				selectedGoods.add(good);
			}
		}
		// 计算分页的起始和结束索引
		int startIndex = (page - 1) * size;  // 从第几条开始
		int endIndex = Math.min(startIndex + size, selectedGoods.size()); // 防止越界

		// 截取指定页的数据
		return selectedGoods.subList(startIndex, endIndex);
	}
	public long getTotalSelectedByType(int typeId, List<Integer> neighbors){
		// 获取所有商品
		List<Goods> goods = goodDao.getAllList();
		List<Goods> selectedGoods = new ArrayList<>();
		// 筛选符合条件的商品
		for(Goods good : goods) {
			if(neighbors.contains(good.getOwnerId()) && good.getTypeId() == typeId) {
				selectedGoods.add(good);
			}
		}return selectedGoods.size();
	}

	/**
	 * 获取数量
	 * @param typeid
	 * @return
	 */
	public long getTotalByType(int typeid){
		return typeid > 0 ? goodDao.getTotalByType(typeid) : goodDao.getTotal();
	}
	
	/**
	 * 通过id获取
	 * @param
	 * @return
	 */
	public Goods get(int id) {
		Goods goods = goodDao.selectById(id);
		if (Objects.nonNull(goods)) {
			goods.setType(iTypeService.get(goods.getTypeId()));
		}
		return goods;
	}
	
	/**
	 * 添加
	 * @param
	 */
	public Integer add(Goods good) {
		return goodDao.insert(good);
	}

	/**
	 * 修改
	 * @param
	 * @return 
	 */
	public boolean update(Goods good) {
		return goodDao.updateById(good) > 0;
	}
	
	/**
	 * 删除商品
	 * 先删除此商品的推荐信息
	 * @param
	 */
	public boolean delete(int goodid) {
		iTopService.deleteByGoodid(goodid);
		return goodDao.deleteById(goodid) > 0;
	}





	/**
	 * 封装商品推荐信息
	 * @param list
	 * @return
	 */
	private List<Goods> packTopList(List<Goods> list) {
		for(Goods good : list) {
			good.setType(iTypeService.get(good.getTypeId()));
			good = packTop(good);
		}
		return list;
	}

	/**
	 * 封装商品推荐信息
	 * @param good
	 * @return
	 */
	private Goods packTop(Goods good) {
		if(good != null) {
			List<Tops> topList = iTopService.getListByGoodid(good.getId());
			if (Objects.nonNull(topList) && !topList.isEmpty()) {
				for(Tops top : topList) {
					if(top.getType()==Tops.TYPE_SCROLL) {
						good.setTopScroll(true);
					}else if (top.getType()==Tops.TYPE_LARGE) {
						good.setTopLarge(true);
					}else if (top.getType()==Tops.TYPE_SMALL) {
						good.setTopSmall(true);
					}
				}
			}
		}
		return good;
	}

}