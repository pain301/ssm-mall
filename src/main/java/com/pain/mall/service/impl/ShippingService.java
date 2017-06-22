package com.pain.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.mapper.ShippingMapper;
import com.pain.mall.pojo.Shipping;
import com.pain.mall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/17.
 */
@Service
public class ShippingService implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int count = shippingMapper.insert(shipping);
        if (0 >= count) {
            return ServerResponse.createByErrorMsg("添加地址失败");
        } else {
            Map result = new HashMap<>();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("添加地址成功", result);
        }
    }

    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        int count = shippingMapper.deleteByUserIdShippingId(userId, shippingId);
        if (0 >= count) {
            return ServerResponse.createByErrorMsg("删除地址失败");
        } else {
            return ServerResponse.createBySuccessMsg("删除地址成功");
        }
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int count = shippingMapper.updateByShipping(shipping);
        if (0 >= count) {
            return ServerResponse.createByErrorMsg("更新地址失败");
        } else {
            return ServerResponse.createBySuccess("更新地址成功");
        }
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByUserIdShippingId(userId, shippingId);
        if (null == shipping) {
            return ServerResponse.createByErrorMsg("查询地址失败");
        } else {
            return ServerResponse.createBySuccess("查询地址成功", shipping);
        }
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
