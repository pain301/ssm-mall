package com.pain.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.mapper.CategoryMapper;
import com.pain.mall.pojo.Category;
import com.pain.mall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/6/12.
 */
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (null == parentId || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMsg("品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);

        // TODO sort order 设置
        category.setStatus(true);

        int count = categoryMapper.insert(category);
        if (0 < count) {
            return ServerResponse.createBySuccessMsg("添加品类成功");
        }
        return ServerResponse.createByErrorMsg("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (null == categoryId || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMsg("品类参数错误");
        }
        Category category = new Category();

        // 防止漏洞
        category.setId(categoryId);
        category.setName(categoryName);

        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (0 < count) {
            return ServerResponse.createBySuccessMsg("更新品类成功");
        }
        return ServerResponse.createByErrorMsg("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectChildCategoryByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("Category {} have no children category", categoryId);
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> getDeepChildCategory(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        getChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (null != categoryId) {
            categoryIdList.addAll(
                    categorySet.stream().map(Category::getId).collect(Collectors.toList()));
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private void getChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);

        if (null != category) {
            categorySet.add(category);
        }

        List<Category> categoryList = categoryMapper.selectChildCategoryByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            getChildCategory(categorySet, categoryItem.getId());
        }
    }
}
