package com.yoyo.controller;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yoyo.entity.*;
import com.yoyo.service.*;
import com.yoyo.util.SafeUtil;
import com.yoyo.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/index")
public class UserController{

	private static final String INDENT_KEY = "order";

	@Resource
	private IUserService iUserService;
	@Resource
	private IOrderService iOrderService;
	@Resource
	private IGoodService iGoodService;
	@Resource
	private ITypeService iTypeService;
	@Autowired
	private ICommunitiesService iCommunitiesService;
	@Autowired
	private ITopService iTopService;


	/**
	 * 注册用户
	 * @return
	 */
	@RequestMapping("/register")
	public String register(@RequestParam(required=false, defaultValue="0")int flag, Users user, Model model, HttpSession session, HttpServletRequest request){
		model.addAttribute("typeList", iTypeService.getList());
		request.setAttribute("communitiesList", iCommunitiesService.getList());
		if(flag==-1) {
			model.addAttribute("flag", 5); // 注册页面
			return "/index/register";
		}
		if (user.getUsername().isEmpty()) {
			model.addAttribute("msg", "用户名不能为空!");
			return "/index/register";
		}else if (iUserService.isExist(user.getUsername())) {
			model.addAttribute("msg", "用户名已存在!");
			return "/index/register";
		}else {
			iUserService.add(user);
			session.setAttribute("msg","注册成功，请登录吧");
			return "redirect:login?flag=-1"; // 注册成功后转去登录
		}
	}

	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@RequestParam(required=false, defaultValue="0")int flag, Users user, HttpSession session, Model model) {
		model.addAttribute("typeList", iTypeService.getList());
		if(flag==-1) {
			model.addAttribute("flag",6);
			return "/index/login";
		}
		if(iUserService.checkUser(user.getUsername(), user.getPassword())){
			System.out.println(123456);
			session.setAttribute("user", iUserService.get(user.getUsername()));
			return "redirect:market";
		} else {
			System.out.println(111123456);
			model.addAttribute("msg", "用户名或密码错误!");
			return "/index/login";
		}
	}


	/**
	 * 用户重置密码
	 * @param user
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/forget")
	public String forget( Users user, HttpSession session, Model model) {
		model.addAttribute("typeList", iTypeService.getList());
		//判断是否存在该用户
		Users users = iUserService.reset(user);
		if(users!=null){
			//重新设置默认密码
			users.setPassword("123456");
			iUserService.updateId(users);
			model.addAttribute("msg", "密码已重置为："+"123456");
			session.setAttribute("user", users);
			return "/index/login"; //跳转到登陆页面
		} else {
			model.addAttribute("msg", "用户名或手机号错误!"); //用户不存在
			return "/index/forget";//返回重置页面并提示
		}
	}


	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("order");
		return "/index/main";
	}

	/**
	 * 查看购物车
	 * @return
	 */
	@RequestMapping("/cart")
	public String cart(Model model) {
		model.addAttribute("typeList", iTypeService.getList());
		return "/index/cart";
	}

	/**
	 * 购买
	 * @return
	 */
	@RequestMapping("/buy")
	public @ResponseBody String buy(ServletRequest request,int goodid, HttpSession session){
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "请登录后加入购物车!");
			return "login";
		}
		Goods goods = iGoodService.get(goodid);
		if (goods.getStock() <= 0) { // 库存不足
			return "empty";
		}


		Orders order = (Orders) session.getAttribute(INDENT_KEY);
		if (order==null) {
			session.setAttribute(INDENT_KEY, iOrderService.add(goods));
		}else {
			session.setAttribute(INDENT_KEY, iOrderService.addOrderItem(order, goods));
		}
		return "ok";
	}

	/**
	 * 减少
	 */
	@RequestMapping("/lessen")
	public @ResponseBody String lessen(int goodid, HttpSession session){
		Orders order = (Orders) session.getAttribute(INDENT_KEY);
		if (order != null) {
			session.setAttribute(INDENT_KEY, iOrderService.lessenIndentItem(order, iGoodService.get(goodid)));
		}
		return "ok";
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody String delete(int goodid, HttpSession session){
		Orders order = (Orders) session.getAttribute(INDENT_KEY);
		if (order != null) {
			session.setAttribute(INDENT_KEY, iOrderService.deleteIndentItem(order, iGoodService.get(goodid)));
		}
		return "ok";
	}


	/**
	 * 提交订单
	 * @return
	 */
	@RequestMapping("/save")
	public String save(ServletRequest request, HttpSession session, Model model){
		model.addAttribute("typeList", iTypeService.getList());
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "请登录后提交订单!");
			return "/index/login";
		}
		Orders sessionOrder = (Orders) session.getAttribute(INDENT_KEY);
		if (sessionOrder != null) {
			if (sessionOrder != null) {
				for(Items item : sessionOrder.getItemList()){ // 检测商品库存(防止库存不足)
					Goods product = iGoodService.get(item.getGoodId());
					if(item.getAmount() > product.getStock()){
						request.setAttribute("msg", "商品 ["+product.getName()+"] 库存不足! 当前库存数量: "+product.getStock());
						return "/index/cart";
					}
				}
			}
			sessionOrder.setUserId(user.getId());
			sessionOrder.setUser(iUserService.get(user.getId()));
			int orderid = iOrderService.save(sessionOrder);	// 保存订单
			session.removeAttribute(INDENT_KEY);	// 清除购物车
			return "redirect:topay?orderid="+orderid;
		}
		request.setAttribute("msg", "处理失败!");
		return "/index/cart";
	}

	/**
	 * 支付页面
	 * @return
	 */
	@RequestMapping("/topay")
	public String topay(int orderid, ServletRequest request, Model model) {
		model.addAttribute("typeList", iTypeService.getList());
		request.setAttribute("order", iOrderService.get(orderid));
		return "/index/pay";
	}

	/**
	 * 支付(模拟)
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(Orders order, Model model) {
		model.addAttribute("typeList", iTypeService.getList());
		iOrderService.pay(order);
		return "redirect:payok?orderid="+order.getId();
	}

	/**
	 * 支付成功
	 * @return
	 */
	@RequestMapping("/payok")
	public String payok(int orderid, ServletRequest request, Model model) {
		model.addAttribute("typeList", iTypeService.getList());
		Orders order = iOrderService.get(orderid);
		int paytype = order.getPaytype();
		if(paytype == Orders.PAYTYPE_WECHAT || paytype == Orders.PAYTYPE_ALIPAY) {
			request.setAttribute("msg", "订单["+orderid+"]支付成功");
		}else {
			request.setAttribute("msg", "订单["+orderid+"]货到付款");
		}
		return "/index/payok";
	}

	/**
	 * 查看订单
	 * @return
	 */
	@RequestMapping("/order")
	public String order(HttpSession session, Model model){
		model.addAttribute("flag", 3);
		model.addAttribute("typeList", iTypeService.getList());
		Users user = (Users) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("msg", "请登录后查看订单!");
			return "/index/login";
		}
		List<Orders> orderList0 = iOrderService.getListByUserid(user.getId());
		if (orderList0!=null && !orderList0.isEmpty()) {
			for(Orders order : orderList0){
				order.setItemList(iOrderService.getItemList(order.getId()));
				order.setUser(iUserService.get(order.getUserId()));
				order.setSeller(iUserService.get(order.getSellerId()));
			}
		}
		model.addAttribute("orderList0", orderList0);
		List<Orders> orderList1 = iOrderService.getListBySellerid(user.getId());
		if (orderList1!=null && !orderList1.isEmpty()) {
			for(Orders order : orderList1){
				order.setItemList(iOrderService.getItemList(order.getId()));
				order.setUser(iUserService.get(order.getUserId()));
				order.setSeller(iUserService.get(order.getSellerId()));
			}
		}
		model.addAttribute("orderList1", orderList1);
		return "/index/order";
	}
	/**
	 * 订单完成
	 *
	 * @return
	 */
	@RequestMapping("/orderFinish")
	public String orderFinish(int id,HttpSession session) {
		session.setAttribute("msg", "确认收货成功!");
		iOrderService.finish(id);
		return "redirect:order" ;
	}
	/**
	 * 订单发货
	 *
	 * @return
	 */
	@RequestMapping("/orderDispose")
	public String orderDispose(int id,HttpSession session ) {
		session.setAttribute("msg", "发货成功!");
		iOrderService.dispose(id);
		return "redirect:order" ;
	}
	/**
	 * 个人信息
	 * @return
	 */
	@RequestMapping("/my")
	public String my(Users user, HttpSession session, Model model){
		model.addAttribute("flag", 4);
		model.addAttribute("typeList", iTypeService.getList());

		// 从 session 中获取已登录用户
		Users userLogin = (Users) session.getAttribute("user");

		if (userLogin == null) {
			model.addAttribute("msg", "请先登录!");
			return "/index/login";
		}
		model.addAttribute("communitiesList", iCommunitiesService.getOtherList(userLogin.getId()));
		for (Communities communities: iCommunitiesService.getOtherList(userLogin.getId())){
			System.out.println("**********************************************************************************" + communities.getName());
		}
		// 检查 user 是否为 null 或 user.getId() 是否为 null
		if (Objects.isNull(user) || Objects.isNull(user.getId())) {
			return "/index/my";
		}

		// 在判空之后访问 user.getId()


		// 修改资料
		Users u = iUserService.get(user.getId());
		u.setName(user.getName());
		u.setPhone(user.getPhone());
		u.setAddress(user.getAddress());
		iUserService.update(u);  // 更新数据库
		session.setAttribute("user", u); // 更新session
		model.addAttribute("msg", "信息修改成功!");

		// 修改密码
		if(user.getPasswordNew() != null && !user.getPasswordNew().trim().isEmpty()) {
			if (user.getPassword() != null && !user.getPassword().trim().isEmpty()
					&& SafeUtil.encode(user.getPassword()).equals(u.getPassword())) {
				if (user.getPasswordNew() != null && !user.getPasswordNew().trim().isEmpty()) {
					u.setPassword(SafeUtil.encode(user.getPasswordNew()));
				}
				iUserService.update(u);  // 更新数据库
				session.setAttribute("user", u); // 更新session
				model.addAttribute("msg", "密码修改成功!");
			} else {
				model.addAttribute("msg", "原密码错误!");
			}
		}

		return "/index/my";
	}



	@RequestMapping("/goodAdd")
	public String goodAdd(HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("typeList", iTypeService.getList());
		return "/index/my_good_add";
	}

	@RequestMapping("/goodSave")
	public String goodSave(String name, int price, String intro, int stock, int typeId,
						   MultipartFile cover, MultipartFile image1, MultipartFile image2,HttpSession session
						   ) throws Exception {
		Users userLogin = (Users) session.getAttribute("user");
		Goods good = new Goods();
		good.setName(name);
		good.setPrice(price);
		good.setIntro(intro);
		good.setStock(stock);
		good.setTypeId(typeId);
		good.setCover(UploadUtil.fileUpload(cover));
		good.setImage1(UploadUtil.fileUpload(image1));
		good.setImage2(UploadUtil.fileUpload(image2));
		good.setOwnerId(userLogin.getId());
		iGoodService.add(good);
		return "redirect:myGoods?flag=5" ;
	}

	/**
	 * 产品更新页面
	 *
	 * @return
	 */
	@RequestMapping("/goodEdit")
	public String goodEdit(int id, HttpServletRequest request) {
		request.setAttribute("flag", 5);
		request.setAttribute("typeList", iTypeService.getList());
		request.setAttribute("good", iGoodService.get(id));
		return "/index/my_good_edit";
	}

	/**
	 * 产品更新
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goodUpdate")
	public String goodUpdate(int id, String name, int price, String intro, int stock, int typeId,
							 MultipartFile cover, MultipartFile image1, MultipartFile image2) throws Exception {
		Goods good = iGoodService.get(id);
		good.setName(name);
		good.setPrice(price);
		good.setIntro(intro);
		good.setStock(stock);
		good.setTypeId(typeId);
		if (Objects.nonNull(cover) && !cover.isEmpty()) {
			good.setCover(UploadUtil.fileUpload(cover));
		}
		if (Objects.nonNull(image1) && !image1.isEmpty()) {
			good.setImage1(UploadUtil.fileUpload(image1));
		}
		if (Objects.nonNull(image2) && !image2.isEmpty()) {
			good.setImage2(UploadUtil.fileUpload(image2));
		}
		iGoodService.update(good);
		return "redirect:myGoods?flag=5" ;
	}

	/**
	 * 产品删除
	 *
	 * @return
	 */
	@RequestMapping("/goodDelete")
	public String goodDelete(int id) {
		iGoodService.delete(id);
		return "redirect:myGoods?flag=5" ;
	}

}