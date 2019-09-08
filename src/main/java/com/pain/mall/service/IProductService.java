package com.pain.mall.service;

import com.github.pagehelper.PageInfo;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Product;
import com.pain.mall.vo.ProductDetailVo;

/**
 * Created by Administrator on 2017/6/13.
 */
public interface IProductService {

    public ServerResponse addOrUpdateProduct(Product product);

    public ServerResponse setSaleStatus(Integer productId, Integer status);

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    public ServerResponse<ProductDetailVo> productDetail(Integer productId);

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId,
                                                  int pageNum, int pageSize);

    public ServerResponse<PageInfo> searchProductByKeyword(String keyWord,
                                                           Integer categoryId,
                                                           int pageNum,
                                                           int pageSize,
                                                           String orderBy);
}
