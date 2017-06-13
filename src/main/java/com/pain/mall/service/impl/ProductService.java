package com.pain.mall.service.impl;

import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.mapper.ProductMapper;
import com.pain.mall.pojo.Product;
import com.pain.mall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/13.
 */
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<String> addOrUpdateProduct(Product product) {
        if (null == product) {
            return ServerResponse.createByErrorMsg("新增或者更新产品参数错误");
        }

        if (StringUtils.isNotBlank(product.getSubImages())) {
            String[] images = product.getSubImages().split(",");
            if (images.length > 0 ) {
                product.setMainImage(images[0]);
            }
        }

        int count = 0;
        if (null != product.getId()) {
            count = productMapper.updateByPrimaryKeySelective(product);
        } else {
            count = productMapper.insert(product);
        }

        if (0 < count) {
            return ServerResponse.createBySuccessMsg("更新或添加产品成功");
        } else {
            return ServerResponse.createByErrorMsg("更新或添加产品失败");
        }
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (null == productId || null == status) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (0 < count) {
            return ServerResponse.createBySuccessMsg("修改产品销售状态成功");
        } else {
            return ServerResponse.createByErrorMsg("修改产品销售状态失败");
        }
    }
}
