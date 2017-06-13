package com.pain.mall.service;

import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Product;

/**
 * Created by Administrator on 2017/6/13.
 */
public interface IProductService {

    public ServerResponse<String> addOrUpdateProduct(Product product);

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status);
}
