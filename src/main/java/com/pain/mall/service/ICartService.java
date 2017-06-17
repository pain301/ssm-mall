package com.pain.mall.service;

import com.pain.mall.common.ServerResponse;
import com.pain.mall.vo.CartVo;

/**
 * Created by Administrator on 2017/6/17.
 */
public interface ICartService {
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    public ServerResponse<CartVo> delete(Integer userId, String productIds);

    public ServerResponse<CartVo> list(Integer userId);

    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    public ServerResponse<Integer> getCartProductCount(Integer userId);

}
