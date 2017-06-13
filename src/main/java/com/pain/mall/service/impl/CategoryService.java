package com.pain.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.mapper.CategoryMapper;
import com.pain.mall.pojo.Category;
import com.pain.mall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/12.
 */
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(String categoryName, Integer parentId) {
        if (null == parentId || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMsg("品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int count = categoryMapper.insert(category);
        if (0 < count) {
            return ServerResponse.createBySuccessMsg("添加品类成功");
        }
        return ServerResponse.createByErrorMsg("添加品类失败");
    }

    @Override
    public ServerResponse<String> updateCategoryName(Integer categoryId, String categoryName) {
        if (null == categoryId || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMsg("品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (0 < count) {
            return ServerResponse.createBySuccessMsg("更新品类成功");
        }
        return ServerResponse.createByErrorMsg("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectChildrenCategoryByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            // TODO logger empty
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> getAllChildrenCategory(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        getChildrenCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (null != categoryId) {
            for (Category category : categorySet) {
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    // TODO performance
    private void getChildrenCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (null != category) {
            categorySet.add(category);
            List<Category> categoryList = categoryMapper.selectChildrenCategoryByParentId(categoryId);
            for (Category categoryItem : categoryList) {
                getChildrenCategory(categorySet, categoryItem.getId());
            }
        }
    }

    private void getChildrenCategory(Set<Category> categorySet, Category category) {
        if (null != category) {
            categorySet.add(category);
            List<Category> categoryList = categoryMapper.selectChildrenCategoryByParentId(category.getId());
            for (Category categoryItem : categoryList) {
                getChildrenCategory(categorySet, categoryItem);
            }
        }
    }
}
