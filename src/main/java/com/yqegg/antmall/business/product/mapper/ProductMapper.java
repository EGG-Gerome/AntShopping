package com.yqegg.antmall.business.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.yqegg.antmall.business.product.entity.Product;

@Repository
// 泛型 <Product> 告诉 MP 应该操作哪个表 (通常根据 Product 类名或其注解确定)，以及操作方法的数据类型。
public interface ProductMapper extends BaseMapper<Product> {

}
