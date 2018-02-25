package com.pain.mall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Product;
import com.pain.mall.pojo.User;
import com.pain.mall.service.IFileService;
import com.pain.mall.service.IProductService;
import com.pain.mall.service.IUserService;
import com.pain.mall.utils.CookieUtil;
import com.pain.mall.utils.JsonUtil;
import com.pain.mall.utils.PropertiesUtil;
import com.pain.mall.utils.RedisPoolUtil;
import com.pain.mall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 */
@Controller
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse saveProduct(HttpServletRequest request, Product product) {
//        String token = CookieUtil.readLoginToken(request);
//
//        if (StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return productService.addOrUpdateProduct(product);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作");
//        }

        return productService.addOrUpdateProduct(product);
    }

    @RequestMapping(value = "set_sale_status", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
//        String token = CookieUtil.readLoginToken(request);
//
//        if (StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return productService.setSaleStatus(productId, status);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作");
//        }

        return productService.setSaleStatus(productId, status);
    }

    @RequestMapping(value = "detail", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<ProductDetailVo> getDetail(HttpServletRequest request, Integer productId) {
//        String token = CookieUtil.readLoginToken(request);
//
//        if (StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return productService.manageProductDetail(productId);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作");
//        }

        return productService.manageProductDetail(productId);
    }

    @RequestMapping(value = "list", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<PageInfo> getList(HttpServletRequest request,
                                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        String token = CookieUtil.readLoginToken(request);
//
//        if (StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return productService.getProductList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作");
//        }

        return productService.getProductList(pageNum, pageSize);
    }

    @RequestMapping(value = "search", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        String token = CookieUtil.readLoginToken(request);
//
//        if (StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            return productService.searchProduct(productName, productId, pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作");
//        }

        return productService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping(value = "upload", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse upload(MultipartFile file, HttpServletRequest request) {
//        String token = CookieUtil.readLoginToken(request);
//
//        if (StringUtils.isBlank(token)) {
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            String path = request.getServletContext().getRealPath("upload");
//            String targetFileName = fileService.upload(file, path);
//
//            String url = PropertiesUtil.getString("ftp.server.http.prefix") + targetFileName;
//            Map fileMap = new HashMap<>();
//            fileMap.put("uri", targetFileName);
//            fileMap.put("url", url);
//
//            return ServerResponse.createBySuccess(fileMap);
//        } else {
//            return ServerResponse.createByErrorMsg("无权限操作");
//        }

        String path = request.getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);

        String url = PropertiesUtil.getString("ftp.server.http.prefix") + targetFileName;
        Map fileMap = new HashMap<>();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);

        return ServerResponse.createBySuccess(fileMap);
    }

    // simditor 富文本
    @RequestMapping(value = "rich_upload", method = {RequestMethod.POST})
    @ResponseBody
    public Map uploadRichText(MultipartFile file,
                              HttpServletRequest request,
                              HttpServletResponse response) {
//        String token = CookieUtil.readLoginToken(request);
//        Map map = new HashMap<>();
//
//        if (StringUtils.isBlank(token)) {
//            map.put("success", false);
//            map.put("msg", "用户未登录");
//            return map;
//        }
//
//        String value = RedisPoolUtil.get(token);
//        User user = JsonUtil.strToObj(value, User.class);
//
//        if (null == user) {
//            map.put("success", false);
//            map.put("msg", "用户未登录");
//            return map;
//        }
//
//        if (userService.checkAdminRole(user).isSuccess()) {
//            String path = request.getServletContext().getRealPath("upload");
//            String targetFileName = fileService.upload(file, path);
//
//            if (StringUtils.isBlank(targetFileName)) {
//                map.put("success", false);
//                map.put("msg", "上传文件失败");
//                return map;
//            }
//
//            String url = PropertiesUtil.getString("ftp.server.http.prefix") + targetFileName;
//            map.put("success", true);
//            map.put("msg", "上传文件成功");
//            map.put("path", url);
//            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
//            return map;
//        } else {
//            map.put("success", false);
//            map.put("msg", "无权限操作");
//            return map;
//        }

        Map map = Maps.newHashMap();
        String path = request.getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);

        if (StringUtils.isBlank(targetFileName)) {
            map.put("success", false);
            map.put("msg", "上传文件失败");
            return map;
        }

        String url = PropertiesUtil.getString("ftp.server.http.prefix") + targetFileName;
        map.put("success", true);
        map.put("msg", "上传文件成功");
        map.put("path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return map;
    }
}
