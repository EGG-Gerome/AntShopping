package com.yqegg.antmall.business.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yqegg.antmall.common.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_product")
public class Product extends CommonEntity{
    // 内部使用，所以用注解。ProductAddBO是外部使用的，所以用注释
    /**
     * 商品名字
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 库存数量
     */
    private Integer stockQuantity;
//  这些都是父类 CommonEntity 有的，所以注释掉了
//    /**
//     * 主键
//     */
//    private Long id;
//
//    /**
//     * 创建时间
//     */
//    private LocalDateTime createTime;
//
//    /**
//     * 修改时间
//     */
//    private LocalDateTime updateTime;
//
//    /**
//     * 删除标记。0：未删除；其他：已删除
//     */
//    private Long deleteFlag;
}