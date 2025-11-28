package com.yqegg.antmall.business.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.yqegg.antmall.business.product.mapper.ProductMapper;
import com.yqegg.antmall.business.product.entity.Product;
import com.yqegg.antmall.business.product.service.ProductService;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
