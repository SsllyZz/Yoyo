package com.yoyo.controller;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.yoyo.entity.*;
import com.yoyo.service.*;
import com.yoyo.util.PageUtil;
import com.yoyo.util.SafeUtil;
import com.yoyo.util.UploadUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 后台相关接口
 */
@Controller
public class AdminController {

    private static final int rows = 10;

    @Autowired
    private IAdminService iAdminService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IGoodService iGoodService;
    @Autowired
    private ITopService iTopService;
    @Autowired
    private ITypeService iTypeService;
    @Autowired
    private ICommunitiesService iCommunitiesService;

    /**
     * 管理员登录
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(Admins admin, HttpServletRequest request) {
        try {
            String encode = SafeUtil.encode(admin.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), encode);//封装用户名和密码到shiro中
            Subject subject = SecurityUtils.getSubject();//得到shiro表示的用户的api对象
            subject.login(token);

            request.setAttribute("msg", "恭喜你! 登录成功了");
            return "/admin/index";
        }catch (UnknownAccountException e){ //登录操作，有可能出现，用户不存在、密码错误
            request.setAttribute("msg","用户不存在");
            System.out.println("用户不存在");
            e.printStackTrace();
        }catch (IncorrectCredentialsException e){
            request.setAttribute("msg","密码错误");
            System.out.println("密码错误");
            e.printStackTrace();
        }catch (LockedAccountException e){
            request.setAttribute("msg","用户已被锁定");
            System.out.println("用户已被锁定");
            e.printStackTrace();
        }catch (AuthenticationException e){
            request.setAttribute("msg","登录失败");
            System.out.println("登录失败");
            e.printStackTrace();
        }
        return "/admin/login";
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/admin/login";
    }

    /**
     * 管理员修改自己密码
     *
     * @return
     */
    @RequestMapping("/adminRe")
    public String adminRe(HttpServletRequest request, HttpSession session) {
        request.setAttribute("flag", 5);
        Admins admins = (Admins) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("username",admins.getUsername());
        request.setAttribute("admin", iAdminService.getByUsername(admins.getUsername()));
        return "/admin/admin_reset";
    }

    /**
     * 管理员修改自己密码
     *
     * @return
     */
    @RequestMapping("/adminReset")
    public String adminReset(Admins admin, HttpServletRequest request) {
        request.setAttribute("flag", 5);
        if (admin.getPassword() != null && !admin.getPassword().trim().isEmpty() && admin.getPasswordNew() != null && !admin.getPasswordNew().trim().isEmpty()){
            if (iAdminService.get(admin.getId()).getPassword().equals(SafeUtil.encode(admin.getPassword()))) {
                admin.setPassword(SafeUtil.encode(admin.getPasswordNew()));
                iAdminService.update(admin);
                return "redirect:logout";
            } else {
                request.setAttribute("msg", "原密码错误!");
                return "forward:adminRe";
            }
        }
        request.setAttribute("msg", "密码不能为空");
        return "forward:adminRe";
    }

    /**
     * 订单列表
     *
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList(@RequestParam(required = false, defaultValue = "0") byte status, HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1") int page) {
        request.setAttribute("flag", 1);
        request.setAttribute("status", status);
        request.setAttribute("orderList", iOrderService.getList(status, page, rows));
        request.setAttribute("pageTool", PageUtil.getPageTool(request, iOrderService.getTotal(status), page, rows));
        return "/admin/order_list";
    }

    /**
     * 订单发货
     *
     * @return
     */
    @RequestMapping("/orderDispose")
    public String orderDispose(int id, byte status,
                               @RequestParam(required = false, defaultValue = "1") int page) {
        iOrderService.dispose(id);
        return "redirect:orderList?flag=1&status=" + status + "&page=" + page;
    }

    /**
     * 订单完成
     *
     * @return
     */
    @RequestMapping("/orderFinish")
    public String orderFinish(int id, byte status,
                              @RequestParam(required = false, defaultValue = "1") int page) {
        iOrderService.finish(id);
        return "redirect:orderList?flag=1&status=" + status + "&page=" + page;
    }

    /**
     * 订单删除
     *
     * @return
     */
    @RequestMapping("/orderDelete")
    public String orderDelete(int id, byte status,
                              @RequestParam(required = false, defaultValue = "1") int page) {
        iOrderService.delete(id);
        return "redirect:orderList?flag=1&status=" + status + "&page=" + page;
    }

    /**
     * 顾客管理
     *
     * @return
     */
    @RequestMapping("/userList")
    public String userList(HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = "1") int page) {
        request.setAttribute("flag", 2);
        request.setAttribute("userList", iUserService.getList(page, rows));
        request.setAttribute("pageTool", PageUtil.getPageTool(request, iUserService.getTotal(), page, rows));
        return "/admin/user_list";
    }

    /**
     * 顾客添加
     *
     * @return
     */
    @RequestMapping("/userAdd")
    public String userAdd(HttpServletRequest request) {
        request.setAttribute("flag", 2);
        request.setAttribute("communitiesList", iCommunitiesService.getList());
        return "/admin/user_add";
    }

    /**
     * 顾客保存
     *
     * @return
     */
    @RequestMapping("/userSave")
    public String userSave(Users user, HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = "1") int page) {
        if (iUserService.isExist(user.getUsername())) {
            request.setAttribute("msg", "用户名已存在!");
            return "/admin/user_add";
        }
        iUserService.add(user);
        return "redirect:userList?flag=2&page=" + page;
    }

    /**
     * 顾客更新页面
     *
     * @return
     */
    @RequestMapping("/userEdit")
    public String userEdit(int id, HttpServletRequest request) {
        request.setAttribute("flag", 2);
        request.setAttribute("user", iUserService.get(id));
        request.setAttribute("communitiesList", iCommunitiesService.getOtherList(id));
        return "/admin/user_edit";
    }

    /**
     * 顾客更新
     *
     * @return
     */
    @RequestMapping("/userUpdate")
    public String userUpdate(Users user,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iUserService.update(user);
        return "redirect:userList?flag=2&page=" + page;
    }

    /**
     * 顾客删除
     *
     * @return
     */
    @RequestMapping("/userDelete")
    public String userDelete(Users user,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iUserService.delete(user);
        return "redirect:userList?flag=2&page=" + page;
    }




    /**
     * 顾客密码重置页面
     *
     * @return
     */
    @RequestMapping("/userRe")
    public String userRe(int id, HttpServletRequest request) {
        request.setAttribute("flag", 2);
        request.setAttribute("user", iUserService.get(id));
        return "/admin/user_reset";
    }

    /**
     * 顾客密码重置
     *
     * @return
     */
    @RequestMapping("/userReset")
    public String userReset(Users user,
                            @RequestParam(required = false, defaultValue = "1") int page) {
        String password = SafeUtil.encode(user.getPassword());
        user = iUserService.get(user.getId());
        user.setPassword(password);
        iUserService.update(user);
        return "redirect:userList?flag=2&page=" + page;
    }

    /**
     * 产品列表
     *
     * @return
     */
    @RequestMapping("/goodList")
    public String goodList(@RequestParam(required = false, defaultValue = "0") byte status, HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = "1") int page) {
        request.setAttribute("flag", 3);
        request.setAttribute("page", page);
        request.setAttribute("status", status);
        request.setAttribute("goodList", iGoodService.getList(status, page, rows));
        request.setAttribute("pageTool", PageUtil.getPageTool(request, iGoodService.getTotal(status), page, rows));
        return "/admin/good_list";
    }

    /**
     * 产品添加页面
     *
     * @return
     */
    @RequestMapping("/goodAdd")
    public String goodAdd(HttpServletRequest request) {
        request.setAttribute("flag", 3);
        request.setAttribute("typeList", iTypeService.getList());
        return "/admin/good_add";
    }

    /**
     * 产品添加
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/goodSave")
    public String goodSave(String name, int price, String intro, int stock, int typeId,int ownerId,
                           MultipartFile cover, MultipartFile image1, MultipartFile image2,
                           @RequestParam(required = false, defaultValue = "1") int page) throws Exception {
        Goods good = new Goods();
        good.setName(name);
        good.setPrice(price);
        good.setIntro(intro);
        good.setStock(stock);
        good.setTypeId(typeId);
        good.setCover(UploadUtil.fileUpload(cover));
        good.setImage1(UploadUtil.fileUpload(image1));
        good.setImage2(UploadUtil.fileUpload(image2));
        good.setOwnerId(ownerId);
        iGoodService.add(good);
        return "redirect:goodList?flag=3&page=" + page;
    }

    /**
     * 产品更新页面
     *
     * @return
     */
    @RequestMapping("/goodEdit")
    public String goodEdit(int id, HttpServletRequest request) {
        request.setAttribute("flag", 3);
        request.setAttribute("typeList", iTypeService.getList());
        request.setAttribute("good", iGoodService.get(id));
        return "/admin/good_edit";
    }

    /**
     * 产品更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/goodUpdate")
    public String goodUpdate(int id, String name, int price, String intro, int stock, int typeId,
                             MultipartFile cover, MultipartFile image1, MultipartFile image2,
                             @RequestParam(required = false, defaultValue = "1") int page) throws Exception {
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
        return "redirect:goodList?flag=3&page=" + page;
    }

    /**
     * 产品删除
     *
     * @return
     */
    @RequestMapping("/goodDelete")
    public String goodDelete(int id,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iGoodService.delete(id);
        return "redirect:goodList?flag=3&page=" + page;
    }

    /**
     * 添加推荐
     *
     * @return
     */
    @RequestMapping("/topSave")
    public @ResponseBody
    String topSave(Tops tops,
                   @RequestParam(required = false, defaultValue = "0") byte status,
                   @RequestParam(required = false, defaultValue = "1") int page) {
        int id = iTopService.add(tops);
        return id > 0 ? "ok" : null;
    }

    /**
     * 删除推荐
     *
     * @return
     */
    @RequestMapping("/topDelete")
    public @ResponseBody
    String topDelete(Tops tops,
                     @RequestParam(required = false, defaultValue = "0") byte status,
                     @RequestParam(required = false, defaultValue = "1") int page) {
        boolean flag = iTopService.delete(tops);
        return flag ? "ok" : null;
    }

    /**
     * 类目列表
     *
     * @return
     */
    @RequestMapping("/typeList")
    public String typeList(HttpServletRequest request) {
        request.setAttribute("flag", 4);
        request.setAttribute("typeList", iTypeService.getList());
        return "/admin/type_list";
    }

    /**
     * 类目添加
     *
     * @return
     */
    @RequestMapping("/typeSave")
    public String typeSave(Types type,
                           @RequestParam(required = false, defaultValue = "1") int page) {
        iTypeService.add(type);
        return "redirect:typeList?flag=4&page=" + page;
    }

    /**
     * 类目更新
     *
     * @return
     */
    @RequestMapping("/typeEdit")
    public String typeUp(int id, HttpServletRequest request) {
        request.setAttribute("flag", 4);
        request.setAttribute("type", iTypeService.get(id));
        return "/admin/type_edit";
    }

    /**
     * 类目更新
     *
     * @return
     */
    @RequestMapping("/typeUpdate")
    public String typeUpdate(Types type,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iTypeService.update(type);
        return "redirect:typeList?flag=4&page=" + page;
    }

    /**
     * 类目删除
     *
     * @return
     */
    @RequestMapping("/typeDelete")
    public String typeDelete(Types type,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iTypeService.delete(type);
        return "redirect:typeList?flag=4&page=" + page;
    }

    /**
     * 管理员列表
     *
     * @return
     */
    @RequestMapping("/adminList")
    public String adminList(HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1") int page) {
        request.setAttribute("flag", 5);
        request.setAttribute("adminList", iAdminService.getList(page, rows));
        request.setAttribute("pageTool", PageUtil.getPageTool(request, iAdminService.getTotal(), page, rows));
        return "/admin/admin_list";
    }





    /**
     * 管理员添加
     *
     * @return
     */
    @RequestMapping("/adminSave")
    public String adminSave(Admins admin, HttpServletRequest request,
                            @RequestParam(required = false, defaultValue = "1") int page) {
        if (iAdminService.isExist(admin.getUsername())) {
            request.setAttribute("msg", "用户名已存在!");
            return "/admin/admin_add";
        }
        iAdminService.add(admin);
        return "redirect:adminList?flag=5&page=" + page;
    }

    /**
     * 管理员修改
     *
     * @return
     */
    @RequestMapping("/adminEdit")
    public String adminEdit(int id, HttpServletRequest request) {
        request.setAttribute("flag", 5);
        request.setAttribute("admin", iAdminService.get(id));
        return "/admin/admin_edit";
    }

    /**
     * 管理员更新
     *
     * @return
     */
    @RequestMapping("/adminUpdate")
    public String adminUpdate(Admins admin,
                              @RequestParam(required = false, defaultValue = "1") int page) {
        admin.setPassword(SafeUtil.encode(admin.getPassword()));
        iAdminService.update(admin);
        return "redirect:adminList?flag=5&page=" + page;
    }

    /**
     * 管理员删除
     *
     * @return
     */
    @RequestMapping("/adminDelete")
    public String adminDelete(Admins admin,
                              @RequestParam(required = false, defaultValue = "1") int page) {
        iAdminService.delete(admin);
        return "redirect:adminList?flag=5&page=" + page;
    }

    /**
     * 小区列表
     *
     * @return
     */
    @RequestMapping("/communitiesList")
    public String communitiesList(HttpServletRequest request) {
        request.setAttribute("flag", 6);
        request.setAttribute("communitiesList", iCommunitiesService.getList());
        return "/admin/communities_list";
    }

    /**
     * 小区添加
     *
     * @return
     */
    @RequestMapping("/communitiesSave")
    public String communitiesSave(Communities communities,
                           @RequestParam(required = false, defaultValue = "1") int page) {
        iCommunitiesService.add(communities);
        return "redirect:communitiesList?flag=6&page=" + page;
    }

    /**
     * 小区更新
     *
     * @return
     */
    @RequestMapping("/communitiesEdit")
    public String communitiesUp(int id, HttpServletRequest request) {
        request.setAttribute("flag", 6);
        request.setAttribute("communities", iCommunitiesService.get(id));
        return "/admin/communities_edit";
    }

    /**
     * 小区更新
     *
     * @return
     */
    @RequestMapping("/communitiesUpdate")
    public String communitiesUpdate(Communities communities,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iCommunitiesService.update(communities);
        return "redirect:communitiesList?flag=6&page=" + page;
    }

    /**
     * 类目删除
     *
     * @return
     */
    @RequestMapping("/communitiesDelete")
    public String communitiesDelete(Communities communities,
                             @RequestParam(required = false, defaultValue = "1") int page) {
        iCommunitiesService.delete(communities);
        return "redirect:communitiesList?flag=6&page=" + page;
    }

}
