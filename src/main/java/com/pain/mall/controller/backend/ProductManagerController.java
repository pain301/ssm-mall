package com.pain.mall.controller.backend;

import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Product;
import com.pain.mall.pojo.User;
import com.pain.mall.service.impl.FileService;
import com.pain.mall.service.impl.ProductService;
import com.pain.mall.service.impl.UserService;
import com.pain.mall.utils.PropertiesUtil;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/13.
 */
@Controller
@RequestMapping(value = "/manager/product")
public class ProductManagerController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/saveProduct", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> saveProduct(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            return productService.addOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "/setSaleStatus", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            return productService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "/detail", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<ProductDetailVo> getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            return productService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            return productService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            return productService.searchProduct(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse upload(HttpSession session, MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            String path = request.getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file, path);

            String url = PropertiesUtil.getValue("ftp.server.http.prefix") + targetFileName;
            Map fileMap = new HashMap<>();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);

            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMsg("无权限操作");
        }
    }

    // simditor 富文本
    @RequestMapping(value = "/upload_richtext_img", method = {RequestMethod.POST})
    @ResponseBody
    public Map uploadRichTextImage(HttpSession session, MultipartFile file,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Map map = new HashMap<>();
        if (null == user) {
            map.put("success", false);
            map.put("msg", "用户未登录");
            return map;
        }
        if (!userService.checkAdminRole(user).isSuccess()) {
            String path = request.getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file, path);

            if (StringUtils.isBlank(targetFileName)) {
                map.put("success", false);
                map.put("msg", "上传文件失败");
                return map;
            }
            String url = PropertiesUtil.getValue("ftp.server.http.prefix") + targetFileName;
            map.put("success", true);
            map.put("msg", "上传文件成功");
            map.put("path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return map;
        } else {
            map.put("success", false);
            map.put("msg", "无权限操作");
            return map;
        }
    }
}
