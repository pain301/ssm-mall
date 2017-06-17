package com.pain.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pain.mall.common.Const;
import com.pain.mall.common.ResponseCode;
import com.pain.mall.common.ServerResponse;
import com.pain.mall.mapper.CategoryMapper;
import com.pain.mall.mapper.ProductMapper;
import com.pain.mall.pojo.Category;
import com.pain.mall.pojo.Product;
import com.pain.mall.service.IProductService;
import com.pain.mall.utils.DateTimeUtil;
import com.pain.mall.utils.PropertiesUtil;
import com.pain.mall.vo.ProductDetailVo;
import com.pain.mall.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

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

    private ProductDetailVo assembleProductDetail(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getValue("ftp.server.prefix", "http://img.pain.mall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (null == category) {
            productDetailVo.setCategoryId(0); // 默认根节点
        } else {
            productDetailVo.setCategoryId(category.getId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (null == productId) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (null == product) {
            return ServerResponse.createByErrorMsg("产品已经下架");
        }

        ProductDetailVo productDetailVo = assembleProductDetail(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServerResponse<ProductDetailVo> productDetail(Integer productId) {
        if (null == productId) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (null == product) {
            return ServerResponse.createByErrorMsg("产品已经下架");
        }

        if (product.getStatus() != Const.ProductStatus.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMsg("商品下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetail(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductVo assembleProductVo(Product product) {
        ProductVo productVo = new ProductVo();
        productVo.setId(product.getId());
        productVo.setName(product.getName());
        productVo.setCategoryId(product.getCategoryId());
        productVo.setImageHost(PropertiesUtil.getValue("ftp.server.prefix", "http://img.pain.mall.com/"));
        productVo.setMainImage(product.getMainImage());
        productVo.setPrice(product.getPrice());
        productVo.setSubtitle(product.getSubtitle());
        productVo.setStatus(product.getStatus());

        return productVo;
    }

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductVo> productVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductVo productVo = assembleProductVo(product);
            productVoList.add(productVo);
        }

        PageInfo pageInfo = new PageInfo(productVoList);
        pageInfo.setList(productVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId,
                                                  int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNoneBlank(productName)) {
            productName = new StringBuffer().append("%").append(productName).append("%").toString();
        }

        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductVo> productVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductVo productVo = assembleProductVo(product);
            productVoList.add(productVo);
        }
        PageInfo pageInfo = new PageInfo(productVoList);
        pageInfo.setList(productVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> searchProductByKeyword(String keyWord,
                                                           Integer categoryId,
                                                           int pageNum,
                                                           int pageSize,
                                                           String orderBy) {
        if (StringUtils.isBlank(keyWord) && null == categoryId) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = new ArrayList<Integer>();
        if (null != categoryId) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (null == category && StringUtils.isBlank(keyWord)) {
                PageHelper.startPage(pageNum, pageSize);
                List<ProductVo> productVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            if (null != category) {
                categoryIdList = categoryService.getAllChildrenCategory(categoryId).getData();
            }
        }
        if (StringUtils.isNoneBlank(keyWord)) {
            keyWord = new StringBuilder().append("%").append(keyWord).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNoneBlank(orderBy)) {
            if (Const.ProductOrderBy.PRICE_ORDER.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyWord) ? null : keyWord,
                0 == categoryIdList.size() ? null : categoryIdList);
        List<ProductVo> productVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductVo productVo = assembleProductVo(product);
            productVoList.add(productVo);
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(productVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
