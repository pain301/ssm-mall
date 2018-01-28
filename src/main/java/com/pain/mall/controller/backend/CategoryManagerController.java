package com.pain.mall.controller.backend;

import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.User;
import com.pain.mall.service.ICategoryService;
import com.pain.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/6/12.
 */
@Controller
@RequestMapping(value = "/manage/category/")
public class CategoryManagerController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = "add_category", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,
                                      String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "set_category_name", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,
                                          Integer categoryId,
                                          String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "get_child_category", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse getChildCategory(HttpSession session,
                                           @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getChildCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "get_deep_child_category", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse getDeepChildCategory(HttpSession session,
                                              @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getDeepChildCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }
}
