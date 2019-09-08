package com.pain.mall.service;

import com.pain.mall.common.ServerResponse;
import com.pain.mall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */
public interface ICategoryService {
    public ServerResponse<String> addCategory(String categoryName, Integer parentId);

    public ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName);

    public ServerResponse<List<Category>> getChildCategory(Integer categoryId);

    public ServerResponse<List<Integer>> getDeepChildCategory(Integer categoryId);
}
