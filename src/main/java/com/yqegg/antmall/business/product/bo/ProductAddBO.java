package com.yqegg.antmall.business.product.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
// 用于‘商品新增’操作的业务对象，接收前端传递的 “添加商品” 所需参数，然后在Service层使用这些数据完成新增逻辑。
public class ProductAddBO {
    // ProductAddBO是外部使用的，所以用注释。Product是内部使用，所以用注解。
    @NotBlank(message = "商品名字不能为空")
    @Schema(description = "商品名字")
    private String name;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品单价")
    private BigDecimal unitPrice;

    @Schema(description = "库存数量")
    private Integer stockQuantity;
}
