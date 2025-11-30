package com.yqegg.antmall.business.product.service.impl;

import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yqegg.antmall.business.product.bo.ProductAddBO;
import com.yqegg.antmall.business.product.bo.ProductEditBO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yqegg.antmall.business.product.mapper.ProductMapper;
import com.yqegg.antmall.business.product.entity.Product;
import com.yqegg.antmall.business.product.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public void add(ProductAddBO addBO) {
        Product product = new Product();
        // 将 ProductAddBO 中的属性复制到 Product 实体中
        BeanUtils.copyProperties(addBO,product);

        save(product);
    }

    @Override
    public void edit(ProductEditBO editBO){
        Product product = new Product();
        BeanUtils.copyProperties(editBO, product);

        updateById(product);
        save(product);
    }
}
