package com.pain.mall.service;

import com.github.pagehelper.PageInfo;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Shipping;

/**
 * Created by Administrator on 2017/6/17.
 */
public interface IShippingService {
    public ServerResponse add(Integer userId, Shipping shipping);

    public ServerResponse delete(Integer userId, Integer shippingId);

    public ServerResponse update(Integer userId, Shipping shipping);

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
