package com.yqegg.antmall.business.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqegg.antmall.business.product.bo.ProductAddBO;
import com.yqegg.antmall.business.product.bo.ProductEditBO;
import com.yqegg.antmall.business.product.bo.ProductQueryBO;
import com.yqegg.antmall.business.product.vo.ProductQueryVO;
import com.yqegg.antmall.common.entity.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.yqegg.antmall.business.product.mapper.ProductMapper;
import com.yqegg.antmall.business.product.entity.Product;
import com.yqegg.antmall.business.product.service.ProductService;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Override
    public void add(ProductAddBO addBO) {
        Product product = new Product();    // 对应表的一行，所以每次都要新建
        // 将 ProductAddBO 中的属性复制到 Product 实体中
        BeanUtils.copyProperties(addBO,product);

        save(product);
    }

    @Override
    public void edit(ProductEditBO editBO){
        Product product = new Product();    // 对应表的一行，所以每次都要新建
        BeanUtils.copyProperties(editBO, product);

        updateById(product);
        save(product);
    }

    @Override
    public PageVO<ProductQueryVO> page(ProductQueryBO queryBO){
        // // 对应表的一行，所以每次都要新建
        Page<Product> pageRequest = new Page<>(queryBO.getCurrentPage(),queryBO.getPageSize());

        // 分页查询的SQL语句
        Page<Product> page = lambdaQuery()
                // condition：布尔值，true = 加入这个查询条件，false = 忽略这个条件
                // column：数据库字段（通过方法引用/字符串指定）
                // val：查询条件的值
                .eq(StringUtils.hasText(queryBO.getName()),
                        Product::getName, queryBO.getName())
                .like(StringUtils.hasText(queryBO.getDescription()),
                        Product::getDescription, queryBO.getDescription())
                .orderByAsc(Product::getCreateTime)
                .page(pageRequest);

        PageVO<ProductQueryVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(page.getCurrent());
        pageVO.setPageSize(page.getSize());
        pageVO.setTotalSize(page.getTotal());

        List<ProductQueryVO> productQueryVOList = new ArrayList<>();
        List<Product> productList = page.getRecords();
        for(Product product : productList){
            ProductQueryVO productQueryVO = new ProductQueryVO();
            BeanUtils.copyProperties(product, productQueryVO);
            productQueryVOList.add(productQueryVO);
        }

        pageVO.setDataList(productQueryVOList);


        return pageVO;
    }
}
