package com.yqegg.antmall.business.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yqegg.antmall.business.product.bo.ProductAddBO;
import com.yqegg.antmall.business.product.bo.ProductEditBO;
import com.yqegg.antmall.business.product.bo.ProductQueryBO;
import com.yqegg.antmall.business.product.entity.Product;
import com.yqegg.antmall.business.product.vo.ProductQueryVO;
import com.yqegg.antmall.common.entity.PageVO;

import java.util.List;

public interface ProductService extends IService<Product> {
    void add(ProductAddBO addBO);
    void edit(ProductEditBO editBO);
    PageVO<ProductQueryVO> page(ProductQueryBO queryBO);
    void delete(List<Long> idList);
}
