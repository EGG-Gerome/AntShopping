package com.yqegg.antmall.business.product.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductEditBO {
    @NotNull(message = "id不能为空 ")
    @Schema(description = "id")
    private Long id;    // 修改的时候指定id

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
