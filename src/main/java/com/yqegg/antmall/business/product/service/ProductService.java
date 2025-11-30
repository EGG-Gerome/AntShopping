package com.yqegg.antmall.business.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yqegg.antmall.business.product.bo.ProductAddBO;
import com.yqegg.antmall.business.product.bo.ProductEditBO;
import com.yqegg.antmall.business.product.entity.Product;

public interface ProductService extends IService<Product> {
    void add(ProductAddBO addBO);
    void edit(ProductEditBO editBO);
}
