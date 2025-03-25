package com.yoyo.controller;

import java.lang.reflect.Type;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yoyo.entity.Goods;
import com.yoyo.entity.Tops;
import com.yoyo.entity.Users;
import com.yoyo.service.IGoodService;
import com.yoyo.service.ITopService;
import com.yoyo.service.ITypeService;
import com.yoyo.service.IUserService;
import com.yoyo.util.PageUtil;
import org.apache.commons.collections.list.TypedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 前台相关接口
 */
@Controller
@RequestMapping("/index")
public class IndexController{
	
	private static final int rows = 16; // 默认每页数量

	@Autowired
	private ITopService iTopService;
	@Autowired
	private IGoodService iGoodService;
	@Autowired
	private ITypeService iTypeService;
	@Autowired
	private IUserService iUserService;
	

	/**
	 * 首页
     */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		return "/index/main";
	}
	/**
	 * 商城首页
     */
	@RequestMapping("/market")
	public String market(HttpServletRequest request){
		request.setAttribute("flag", 1);
		Users userLogin = (Users) request.getSession().getAttribute("user");
		List<Integer>neighbors=iUserService.neighbors(userLogin);

		List<Tops>top1List=iTopService.getSelectList(neighbors,Tops.TYPE_SCROLL, 1, 1);
		List<Tops>top2List=iTopService.getSelectList(neighbors,Tops.TYPE_LARGE, 1, 6);
		List<Tops>top3List=iTopService.getSelectList(neighbors,Tops.TYPE_SMALL, 1, 8);
		request.setAttribute("typeList", iTypeService.getList());
		request.setAttribute("top1List", top1List);
		request.setAttribute("top2List", top2List);
		request.setAttribute("top3List", top3List);
		return "/index/market";
	}

	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping("/goods")
	public String goods(int typeid, @RequestParam(required=false, defaultValue="1")int page, HttpServletRequest request){
		request.setAttribute("flag", 2);
		if (typeid > 0) {
			request.setAttribute("type", iTypeService.get(typeid));
		}
		Users userLogin = (Users) request.getSession().getAttribute("user");
		List<Integer>neighbors=iUserService.neighbors(userLogin);
		request.setAttribute("typeList", iTypeService.getList());
		request.setAttribute("goodList", iGoodService.getSelectedListByType(neighbors, typeid, page, rows));
		request.setAttribute("pageTool", PageUtil.getPageTool(request,iGoodService.getTotalSelectedByType(typeid,neighbors), page, rows));
		return "/index/goods";
	}

	/**
	 * 推荐列表
	 * @return
	 */
	@RequestMapping("/top")
	public String tops(int typeid, @RequestParam(required=false, defaultValue="1")int page, HttpServletRequest request) {
		Users userLogin = (Users) request.getSession().getAttribute("user");
		List<Integer>neighbors=iUserService.neighbors(userLogin);
		request.setAttribute("typeList", iTypeService.getList());
		List<Goods> list0=iGoodService.getSelectedList(neighbors,page, rows);
		List<Goods> list1=new ArrayList<>();
		List<Goods> list2=new ArrayList<>();
		List<Tops>top1List=iTopService.getSelectList(neighbors,Tops.TYPE_LARGE, 1, 6);
		List<Tops>top2List=iTopService.getSelectList(neighbors,Tops.TYPE_SMALL, 1, 8);
		for(Goods good:list0){
			for(Tops top:top1List){
				if (good.getId().equals(top.getGoodId())){
					list1.add(good);
				}
			}
			for(Tops top:top2List){
				if (good.getId().equals(top.getGoodId())){
					list2.add(good);
				}
			}
		}

		if (typeid==1){
			request.setAttribute("flag", 6);
			request.setAttribute("goodList",list0);
			request.setAttribute("pageTool", PageUtil.getPageTool(request, iGoodService.getTotalSelected(neighbors), page, rows));
		}else if (typeid==2){
			request.setAttribute("flag", 7);
			request.setAttribute("goodList", list1);
			request.setAttribute("pageTool", PageUtil.getPageTool(request, list1.size(), page, rows));

		}else if (typeid==3){
			request.setAttribute("flag", 8);
			request.setAttribute("goodList", list2);
			request.setAttribute("pageTool", PageUtil.getPageTool(request, list2.size(), page, rows));
		}

		return "/index/goods";
	}

	/**
	 * 搜索
	 * @return
	 */
	@RequestMapping("/search")
	public String search(String name, @RequestParam(required=false, defaultValue="1")int page, HttpServletRequest request) {
		if (Objects.nonNull(name) && !name.trim().isEmpty()) {
			Users user= (Users) request.getSession().getAttribute("user");
			List<Integer>neighbors=iUserService.neighbors(user);
			List<Goods>goodsList=new ArrayList<>();
			List<Goods>resultList=new ArrayList<>();
			for (Integer i : neighbors) {
				Users user0=iUserService.get(i);
				goodsList.addAll(iGoodService.getMyList(user0));
			}
			for (Goods good : goodsList) {
				if (good.getName().contains(name)) {
					resultList.add(good);
				}
			}
			request.setAttribute("goodList",resultList);
			request.setAttribute("pageTool", PageUtil.getPageTool(request, iGoodService.getTotalByName(name), page, rows));
		}
		request.setAttribute("typeList", iTypeService.getList());
		return "/index/goods";
	}

	/**
	 * 商品详情
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(int goodid, HttpServletRequest request){
		request.setAttribute("good", iGoodService.get(goodid));
		request.setAttribute("typeList", iTypeService.getList());
		return "/index/detail";
	}
	/**
	 * 产品列表
	 *
	 * @return
	 */
	@RequestMapping("/myGoods")
	public String goodList(HttpSession session, Model model) {
		model.addAttribute("flag", 5);
		Users userLogin = (Users) session.getAttribute("user");
		model.addAttribute("goodList", iGoodService.getMyList(userLogin));
		model.addAttribute("typeList", iTypeService.getList());
		return "/index/my_goods";
	}

}