package com.pain.mall.controller.portal;

import com.pain.mall.common.ServerResponse;
import com.pain.mall.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/6/16.
 */
@Controller
@RequestMapping(value = "/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "detail", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse productDetail(Integer productId) {
        return productService.productDetail(productId);
    }

    @RequestMapping(value = "search", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse searchProduct(@RequestParam(value = "keyword", required = false) String keyword,
                                        @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return productService.searchProductByKeyword(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
