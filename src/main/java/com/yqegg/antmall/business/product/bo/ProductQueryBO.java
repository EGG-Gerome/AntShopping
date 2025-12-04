package com.yqegg.antmall.business.product.bo;

import com.yqegg.antmall.common.entity.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductQueryBO extends PageBO {
    // 查询的时候不能限制一定非空
    @Schema(description = "商品名字")
    private String name;

    @Schema(description = "商品描述")
    private String description;
}
